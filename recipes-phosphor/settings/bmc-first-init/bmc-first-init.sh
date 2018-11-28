#!/bin/bash -eu

show_error() {
    logger -p user.error -t bmc-first-init $@
}

NETWORK_ITEM_IFACE='xyz.openbmc_project.Inventory.Item.NetworkInterface'
NETWORK_ITEM_PATH='/xyz/openbmc_project/inventory{bmc_network_item_path}'
NETWORK_ITEM_OBJ=$(mapper get-service ${NETWORK_ITEM_PATH} 2>/dev/null || true)

if [[ -z "${NETWORK_ITEM_OBJ}" ]]; then
    show_error 'No Ethernet interface found in the Inventory. Is VPD EEPROM empty?'
    exit 1
fi

MAC_ADDR=$(busctl get-property ${NETWORK_ITEM_OBJ} \
                               ${NETWORK_ITEM_PATH} \
                               ${NETWORK_ITEM_IFACE} MACAddress)
if [[ -n "${MAC_ADDR}" ]]; then
    # 's "54:52:01:02:03:04"'
    MAC_ADDR=${MAC_ADDR:2}
    MAC_ADDR=${MAC_ADDR#\"}
    MAC_ADDR=${MAC_ADDR%\"}

    busctl set-property xyz.openbmc_project.Network \
                        /xyz/openbmc_project/network/eth0 \
                        xyz.openbmc_project.Network.MACAddress \
                        MACAddress s ${MAC_ADDR}
fi

systemctl disable bmc-first-init.service
