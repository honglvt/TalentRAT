module.exports = class Author {
    constructor(cmd2Clients, cmdFromClient, data) {
        this.cmd2Clients = cmd2Clients;
        this.data = data;
        this.cmdFromClient = cmdFromClient;
    };

    /**
     * the commen action to give an order to each client or all clients
     * and observed the data from clients 
     * you should override the fun ownerAction to do sth. yourself
     */
    distribute(IMEI, req, res, next) {
        this.res = res;
        this.req = req;
        if (IMEI) {
            global.sockets.get(IMEI).emit(
                this.cmd2Clients, this.data
            );
            this.data = '';
        };
    }

    ownerAction(data) {

    }
}  