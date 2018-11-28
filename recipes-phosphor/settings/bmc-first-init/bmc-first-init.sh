#!/bin/bash -eu

check_ethernet=$(busctl call xyz.openbmc_project.Inventory.Manager \
                    /xyz/openbmc_project/inventory \
                    org.freedesktop.DBus.ObjectManager \
                    GetManagedObjects --no-pager --verbose \
                | grep xyz.openbmc_project.Inventory.Item.NetworkInterface || true)

if [[ -z "${check_ethernet}" ]]
then
    logger -p user.error 'No Ethernet MACAddress in the Inventory. VPD EEPROM is empty.'
    exit 0
else
    mac=$(busctl get-property xyz.openbmc_project.Inventory.Manager \
            /xyz/openbmc_project/inventory/system/chassis/ethernet \
            xyz.openbmc_project.Inventory.Item.NetworkInterface MACAddress)
    if [[ -n "${mac}" ]]; then
       mac=${mac:2}
       mac=${mac#\"}
       mac=${mac%\"}
       busctl set-property xyz.openbmc_project.Network \
            /xyz/openbmc_project/network/eth0 \
            xyz.openbmc_project.Network.MACAddress \
            MACAddress s ${mac}
    fi
fi

systemctl disable bmc-first-init.service
