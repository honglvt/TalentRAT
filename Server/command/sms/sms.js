const Cmd2Clients = require('../cmds/command2clients');
const CmdFromClients = require('../cmds/commandFromClient');
let Author = require('../author');
let msg = "post sms to server"

module.exports = class Sms extends Author {

    constructor() {
        super(Cmd2Clients.SEND_SMS_LIST,
            CmdFromClients.SMS_LIST,
            msg)
    }

    ownerAction(data) {
        super.ownerAction(data);
        this.res.json(data)
    }
}