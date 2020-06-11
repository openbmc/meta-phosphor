inherit phosphor-dbus-yaml

LOGGING_YAML_SUBDIRS ??= "xyz/openbmc_project"

do_install() {
    for yaml_d in ${LOGGING_YAML_SUBDIRS} ;
    do
        for yaml_f in $(find ${S}/${yaml_d} -name "*.error.yaml" -or \
            -name "*.metadata.yaml") ;
        do
            subpath=$(realpath --relative-to=${S} ${yaml_f})
            echo "${yaml_f} to ${subpath}"
            install -d ${D}${yaml_dir}/$subpath
            install -m 0644 ${yaml_f} ${D}${yaml_dir}/$subpath
        done
    done
}
