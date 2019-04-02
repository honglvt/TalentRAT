const Cmd2Clients = require('../cmds/command2clients');
const CmdFromClients = require('../cmds/commandFromClient');
let Author = require('../author');
let msg = 'send sms list to me';
module.exports = class Sms extends Author {

    constructor() {
        super(Cmd2Clients.SEND_SMS_LIST,
            CmdFromClients.SMS_LIST,
            msg);
    }

    distribute(IMEI, req, res, next) {
        let params = req.body;

        if (params.address && params.content) {
            let json = JSON.stringify({
                address: params.address,
                content: params.content
            });
            this.data = json;
            console.log(this.data);
        }
        super.distribute(IMEI, req, res, next)
    }

    ownerAction(data) {
        super.ownerAction(data);
        this.res.json(data)
    }
}