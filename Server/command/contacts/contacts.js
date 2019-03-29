let Author = require('../author');
const CmdFromClients = require('../cmds/commandFromClient');
const Cmd2Clients = require('../cmds/command2clients');
module.exports = class Contact extends Author {
    constructor() {
        super(Cmd2Clients.SEND_CONTACTS_LIST,
            CmdFromClients.CONTACTS_LIST
            , 'send the contacts to server');
    }
    ownerAction(data) {
        super.ownerAction(data);
        this.res.json(data);
    }
}
