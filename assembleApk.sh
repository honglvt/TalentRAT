#!/bin/bash

ENV=$1
MACHINE=$2

echo $MACHINE
GRADLE_FILE="./app/build.gradle"

if [ -z "$MACHINE" ]; then
  MACHINE="mac"
fi

if [ -z "$ENV" ]; then
  ENV="debug"
fi

if [ $ENV != 'release' ]; then
    echo 'append dev string to versionName'
    if [ $MACHINE != 'mac' ]; then
    sed -i 's@versionName.*versionPatch)$@&\+"dev"@' $GRADLE_FILE
    else
        sed -i '' 's@versionName.*versionPatch)$@&\+"dev"@' $GRADLE_FILE
    fi
else
    echo 'remove dev string from versionName'
    if [ $MACHINE != 'mac' ]; then
    sed -i 's@\+"dev"@@' $GRADLE_FILE
    else
        sed -i '' 's@\+"dev"@@' $GRADLE_FILE 
    fi
fi


get_uppercase_env() {
  current_env=$1
  # make first letter upper case
  local result="$(tr '[:lower:]' '[:upper:]' <<< ${current_env:0:1})${current_env:1}"
  echo $result
}

build_action="assemble$(get_uppercase_env $ENV)"

echo "############### begin $build_action ###################"

./gradlew $build_action

echo "############## finish $build_action ####################"

