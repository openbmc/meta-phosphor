# WARNING!
#
# These modifications to os-release disable the bitbake parse
# cache (for the os-release recipe only).  Before copying
# and pasting into another recipe ensure it is understood
# what that means!

def run_git(d, cmd):
    try:
        oeroot = d.getVar('COREBASE', True)
        return bb.process.run("git --work-tree %s --git-dir %s/.git %s"
            % (oeroot, oeroot, cmd))[0].strip('\n')
    except:
        pass


OS_RELEASE_FIELDS_append = " BUILD_ID OPENBMC_TARGET_MACHINE"

# Ensure the git commands run every time bitbake is invoked.
BB_DONT_CACHE = "1"

# Make os-release available to other recipes.
SYSROOT_DIRS_append = " ${sysconfdir}"


# OpenBMC's usage of os-release is somewhat complicated
# This do_updater step has been added at the end of
# the build process and re-generates the entire os-release
# file with the appropriate settings for the OpenBMC project.
python do_updater () {

    if not os.path.exists('${B}'):
        os.makedirs('${B}')
    f = open(d.expand('${B}/os-release'), 'w')

    version_id = run_git(d, 'describe --dirty --long')
    if version_id:
        d.setVar('VERSION_ID', version_id)
        versionList = version_id.split('-')
        version = versionList[0] + "-" + versionList[1]
        d.setVar('VERSION', version)

    build_id = run_git(d, 'describe --abbrev=0')
    if build_id:
        d.setVar('BUILD_ID', build_id)

    target_machine = d.getVar('MACHINE', True)
    if target_machine:
        d.setVar('OPENBMC_TARGET_MACHINE', target_machine)

    # Now regenerate the os-release file with update fields
    for field in d.getVar('OS_RELEASE_FIELDS').split():
        unquotedFields = d.getVar('OS_RELEASE_UNQUOTED_FIELDS').split()
        value = d.getVar(field)
        if value:
            if field in unquotedFields:
                value = sanitise_value(value)
                f.write('{0}={1}\n'.format(field, value))
            else:
                f.write('{0}="{1}"\n'.format(field, value))
}

# Create a special task to ensure os-release is updated every build
addtask updater after do_image before do_build

# Build updater every time we build an image
do_updater[nostamp] = "1"

# Ensure we install the os-release every time as well
do_install[nostamp] = "1"
