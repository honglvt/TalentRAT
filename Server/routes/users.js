var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/users', function (req, res, next) {
  console.log(global.userMap);
  let list = [];

  for (let [key, value] of global.userMap) {
    list.push(value);
  }
  console.log(list);
  res.json(list);
});

module.exports = router;
