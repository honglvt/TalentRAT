const Cmd2Clients = require('../cmds/command2clients');
const CmdFromClients = require('../cmds/commandFromClient');
let Author = require('../author');
let msg = 'send me the gps to me';
module.exports = class Gps extends Author {
    constructor() {
        super(
            Cmd2Clients.SEND_GPS,
            CmdFromClients.GPS,
            msg
        )
    }

    ownerAction(data) {
        super.ownerAction(data);
        return this.res.json(data);
    }
}