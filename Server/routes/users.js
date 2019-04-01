var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/users', function (req, res, next) {
  console.log(global.userList);
  res.json(global.userList);
});

module.exports = router;
