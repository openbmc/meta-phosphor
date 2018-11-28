#!/bin/bash -eu

show_error() {
    logger -p user.error -t bmc-first-init $@
}

MAPPER_IFACE='xyz.openbmc_project.ObjectMapper'
MAPPER_PATH='/xyz/openbmc_project/object_mapper'
INVENTORY_PATH='/xyz/openbmc_project/inventory'
NETWORK_ITEM_IFACE='xyz.openbmc_project.Inventory.Item.NetworkInterface'

NETWORK_ITEM_PATH=$(busctl --no-pager --verbose call \
                           ${MAPPER_IFACE} \
                           ${MAPPER_PATH} \
                           ${MAPPER_IFACE} \
                           GetSubTree sias \
                                ${INVENTORY_PATH} 0 1 ${NETWORK_ITEM_IFACE} \
                    2>/dev/null | grep ${INVENTORY_PATH} || true)

# '     STRING "/xyz/openbmc_project/inventory/system/chassis/ethernet";'
NETWORK_ITEM_PATH=${NETWORK_ITEM_PATH#*\"}
NETWORK_ITEM_PATH=${NETWORK_ITEM_PATH%\"*}

NETWORK_ITEM_OBJ=$(mapper get-service ${NETWORK_ITEM_PATH} 2>/dev/null || true)

if [[ -z "${NETWORK_ITEM_OBJ}" ]]; then
    show_error 'No Ethernet interface found in the Inventory. Is VPD EEPROM empty?'
    exit 1
fi

MAC_ADDR=$(busctl get-property ${NETWORK_ITEM_OBJ} \
                               ${NETWORK_ITEM_PATH} \
                               ${NETWORK_ITEM_IFACE} MACAddress)

# 's "54:52:01:02:03:04"'
MAC_ADDR=${MAC_ADDR#*\"}
MAC_ADDR=${MAC_ADDR%\"*}

if [[ -n "${MAC_ADDR}" ]]; then
    busctl set-property xyz.openbmc_project.Network \
                        /xyz/openbmc_project/network/eth0 \
                        xyz.openbmc_project.Network.MACAddress \
                        MACAddress s ${MAC_ADDR}
fi

systemctl disable bmc-first-init.service
