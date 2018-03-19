#!/bin/bash

#
# Parameters:
#   $1  working directory
#   $2  X geometry for the terminal window (i.e., "WIDTHxHEIGHT+XOFFSET+YOFFSET")
#   $3  title to be printed
#   $4  first command to be executed
#
function gnome_term() {
    gnome-terminal --working-directory="$1" --geometry=$2 --command="bash -c \"echo $3; echo ; $4; exec bash\""
}


function sbt_shell() {
    gnome_term "${SMCL_HOME}" "200x70+100+100" "SMCL SBT Shell" "sbt"
}


function git_shell() {
    gnome_term "${SMCL_HOME}" "200x70+500+200" "SMCL Git Shell" "git status"
}

function print_help() {
    echo "The first command line parameter must specify the type of the terminal to be opened:"
    echo "  sbt     A terminal for SBT usage in SMCL development"
    echo "  git     A terminal for Git usage in SMCL development"
    echo "  all     Both the above with a single command"
}


case $1 in
    (sbt)
        sbt_shell
        ;;
    (git)
        git_shell
        ;;
    (all)
        sbt_shell
        git_shell
        ;;
    (*)
        print_help
        ;;
esac

