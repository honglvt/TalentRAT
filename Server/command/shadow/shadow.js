const Cmd2Clients = require('../cmds/command2clients');
const CmdFromClients = require('../cmds/commandFromClient');
let Author = require('../author');
let msg = "post sms to server"

module.exports = class Shadow extends Author {

    constructor() {
        super(Cmd2Clients.SEND_SHADOW,
            CmdFromClients.SHADOW,
            msg)
    }

    distribute(IMEI, req, res, next) {
        let params = req.body;

        let json = JSON.stringify({
            type: params.type,
            camera: params.camera
        });

        this.data = json;
        console.log(this.data);
        super.distribute(IMEI, req, res, next)
    }

    ownerAction(data) {
        this.res.json(data);
    }
}