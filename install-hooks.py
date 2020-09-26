import sys
from os import path
import urllib; from urllib.request import urlretrieve
from subprocess import call

def install_hooks(directory):
    checkstyleUrl = 'https://github.com/checkstyle/checkstyle/releases/download/checkstyle-8.36.1/checkstyle-8.36.1-all.jar'
    preCommitUrl = 'https://srv-file10.gofile.io/downloadStore/srv-store1/ulBF6S/pre-commit'
    checkstyleName = checkstyleUrl.split('/')[len(checkstyleUrl.split('/')) - 1]
    basePath = path.abspath(directory)
    print("Downloading checkstyle to %s..." % basePath + "/.git/hooks/" + checkstyleName)
    urlretrieve(checkstyleUrl, basePath + "/.git/hooks/" + checkstyleName)
    print("Downloading pre-commit script to %s" % basePath + "/.git/hooks/pre-commit")
    urlretrieve(preCommitUrl, basePath + "/.git/hooks/pre-commit")
    with open(basePath + '/.git/config', 'a+') as gitConfig:
        if ("[checkstyle]" not in gitConfig.read()):
            print("Adding git configurations to .git/config")
            gitConfig.write("[checkstyle]\n")
            gitConfig.write("jar = %s\n" % (basePath + "/.git/hooks/" + checkstyleName))
            gitConfig.write("checkfile = %s\n" % (basePath + "/checkstyle_config.xml"))
    print("Changing permissions for pre-commit. Has to run as root, enter password plz")
    call(["sudo", "chmod", "+x", (basePath + "/.git/hooks/pre-commit")])


if __name__ == "__main__":
    if (len(sys.argv) < 2):
        print("Enter a directory to install hooks")
    else:
        if (path.exists(sys.argv[1])):
            install_hooks(sys.argv[1])
