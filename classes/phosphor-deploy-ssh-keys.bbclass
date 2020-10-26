# Copyright 2020 Hewlett Packard Enterprise Development LP.

inherit useradd_base

deploy_local_user () {
        if [ "${SSH_KEYS}" != "" ]; then

                username=`echo ${SSH_KEYS} | awk -F":" '{ print $1}'`
                key_path=`echo ${SSH_KEYS} | awk -F":" '{ print $2}'`

                if [ ! -d ${IMAGE_ROOTFS}/home/${username} ]; then
                        perform_useradd "${IMAGE_ROOTFS}" "-R ${IMAGE_ROOTFS} -p '' ${username}"
                        perform_groupadd "${IMAGE_ROOTFS}" "-R ${IMAGE_ROOTFS} titi"
                fi

                if [ ! -d ${IMAGE_ROOTFS}/home/${username}.ssh/ ]; then
                        install -d ${IMAGE_ROOTFS}/home/${username}/.ssh/
                fi

                if [ ! -f ${IMAGE_ROOTFS}/home/${username}/.ssh/authorized_keys ]; then
                        install -m 0600 ${key_path} ${IMAGE_ROOTFS}/home/${username}/.ssh/authorized_keys
                else
                        cat ${key_path} >> ${IMAGE_ROOTFS}/home/${username}/.ssh/authorized_keys
                fi

                uid=`cat ${IMAGE_ROOTFS}/etc/passwd | grep "${username}:" | awk -F ":" '{print $3}'`
                guid=`cat ${IMAGE_ROOTFS}/etc/passwd | grep "${username}:" | awk -F ":" '{print $4}'`

                chown -R ${uid}:${guid} ${IMAGE_ROOTFS}/home/${username}/.ssh
                chmod 600  ${IMAGE_ROOTFS}/home/${username}/.ssh/authorized_keys
                chmod 700 ${IMAGE_ROOTFS}/home/${username}/.ssh

                is_group=`grep "priv-admin" ${IMAGE_ROOTFS}/etc/group || true`

                if [ -z "${is_group}" ]; then
                        perform_groupadd "${IMAGE_ROOTFS}" "-R ${IMAGE_ROOTFS} priv-admin"
                fi

                priv=`grep priv-admin ${IMAGE_ROOTFS}/etc/group`
                is_declared=`grep ${username} ${priv} || true`
                if [ -z "${is_declared}" ]; then
                        grep -v "priv-admin" ${IMAGE_ROOTFS}/etc/group > ${IMAGE_ROOTFS}/etc/group.new
                        cp ${IMAGE_ROOTFS}/etc/group.new ${IMAGE_ROOTFS}/etc/group
                        echo "${priv},${username}" >> ${IMAGE_ROOTFS}/etc/group
                        rm ${IMAGE_ROOTFS}/etc/group.new
                fi
        fi
}
