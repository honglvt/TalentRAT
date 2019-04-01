#!/bin/bash

apk=`find . -name "*.apk" | xargs ls -ta | head -n 1`

echo "################# begin install $apk #################"
adb install -r $apk
