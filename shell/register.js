const EnAiPackageName = "com.xingin.xhs";
auto();
// console.show();
let count = 0;
let launch = function () {
    app.launch(EnAiPackageName);
    waitForActivity("com.xingin.xhs.index.IndexNewActivity", 200);
    clickSearch();
    let mine = id("com.xingin.xhs:id/a3o");
    mine.click(); //切换tab点击我的
    sleep(1000);
    let setting = id("com.xingin.xhs:id/a2w");
    setting.click();
    sleep(1000);

    //退出登录
    var c = className("TextView").find();
    if (c.empty()) {
        toast("mei找到啦");
    } else {
        toast(c.size() + "");
        c.get(14).click();
    }
    sleep(1000);
    //退出登录toast 确定按键
    var confirm = id("android:id/button1");
    confirm.click();
    toast("点击了确认");
    switchAccount();
}

/**
 * 更换账号
 */
switchAccount = function () {
    login();
}
/**
 * 登录
 */
login = function () {
    clickMine();
    waitForActivity("com.xingin.login.activity.LoginActivity", 200);
    let btnWeiBo = id("com.xingin.xhs:id/brl");
    btnWeiBo.click();
}

let token = "00516259";
let itemId = 190;
register = function () {
    if (count == 0) {
        app.launch(EnAiPackageName);
        waitForActivity("com.xingin.xhs.index.IndexNewActivity", 200)
    }
    console.log("IndexNewActivity");
    clickSearch();
    clickMine();
    console.log(currentActivity());
    if (currentActivity() == "com.xingin.login.activity.LoginActivity") {
        console.log("currentActivity is LoginActivity");
        getphone()
    } else {
        clickSetting();
        clickLoginOut();
        getphone();
    }

}
clickMine = function () {
    sleep(1000);
    let mine = id("com.xingin.xhs:id/a54");
    mine.click(); //切换tab点击我的
}
/**
 * 获取手机号
 */
getphone = function () {
    sleep(1000);
    waitForActivity("com.xingin.login.activity.LoginActivity");
    http.get("http://api.fxhyd.cn/UserInterface.aspx?action=getmobile&token=" + token + "&itemid=" + itemId + "&isp=3", {}, (res, err) => {
        console.log(getphone);
        if (err) {
            toast("error request");
        } else {
            let phone = res.body.string().substring(8);
            let etUserName = id("com.xingin.xhs:id/al7");
            etUserName.setText(phone);
            console.log(phone);
            clickGetCode();
            getVerifyCode(phone);
        }
    });
}

/**
 * 获取验证码
 */
getVerifyCode = function (phoneNum) {
    sleep(1000);
    console.log("--------getVerifyCode-------");
    sleep(5000);
    http.get("http://api.fxhyd.cn/UserInterface.aspx?action=getsms&token=" + token + "&itemid=" + itemId + "&mobile=" + phoneNum, {}, (res, err) => {
        if (err) {
            console.log("--------getVerifyCode ERROR-------");
        } else {
            var response = res.body.string();
            if (response == "3001") {
                console.log(" res is empty");
                getVerifyCode(phoneNum);
            } else {
                console.log(response);
                let etPassword = id("com.xingin.xhs:id/lk");
                let verifyStart = response.indexOf("验证码是") + 5;
                let end = verifyStart + 7;
                let password = response.substring(verifyStart, end).trim();
                console.log(password);
                sleep(2000);
                etPassword.setText(password.substring(0, 1));
                etPassword.setText(password.substring(0, 2));
                etPassword.setText(password.substring(0, 3));
                etPassword.setText(password.substring(0, 4));
                etPassword.setText(password.substring(0, 5));
                etPassword.setText(password.substring(0, 6));
                clickLogin();
                sleep(1000);
                count = 1;
                register();
                // waitForActivity("com.xingin.login.activity.LoginActivity");

                // clickMine();
                // clickSetting();
                // clickAccount();
                // clickPasswordSeeting();
                // setNewPassword();
            }
        }
        // console.hide();
    });
}

clickGetCode = function () {
    sleep(1000);
    let getCode = id("com.xingin.xhs:id/lj");
    getCode.click();
    console.log("--------clickGetCode-------")
}

clickSetting = function () {
    sleep(1000);
    //设置界面
    let setting = id("com.xingin.xhs:id/a4a");
    setting.click();
    console.log("--------setting-------")
}

clickLoginOut = function () {
    sleep(1000);
    var c = className("TextView").find();
    if (c.empty()) {
        toast("mei找到啦");
    } else {
        toast(c.size() + "");
        c.get(13).click();
    }
    // var c = desc("登出账户");
    sleep(1000);
    var confirm = id("android:id/button1");
    confirm.click();
    toast("点击了确认");
}

clickLogin = function () {
    sleep(2000);
    // let btnLogin = id("com.xingin.xhs:id/ajb");
    // btnLogin.click();
    press(605, 1040, 1500);
}

clickSearch = function () {
    //进入搜索页
    console.log("进入搜索页");
    sleep(1000);
    let search = id("com.xingin.xhs:id/a2s");
    search.click();
    sleep(1000);
    //输入信息
    let input = id("com.xingin.xhs:id/ape");
    input.setText("七黎橙");
    sleep(1000);
    //点击查找
    let btnSearch = id("com.xingin.xhs:id/aph");
    btnSearch.click();
    clickAttention();
}

clickAttention = function () {
    sleep(1000);
    click(352, 648);
    sleep(2000);
    let attention = text("关注");
    attention.click();
    sleep(1000);
    back();
    sleep(1000);
    back();
    sleep(1000);
    back();
    sleep(1000);
    back();
    sleep(1000);
}

clickAccount = function () {
    sleep(1000);
    var c = className("TextView").find();
    if (c.empty()) {
        toast("mei找到啦");
    } else {
        toast(c.size() + "");
        c.get(3).click();
    }
}
clickPasswordSeeting = function () {
    sleep(1000);
    click("登录密码")
}
setNewPassword = function () {
    etFirst = id("com.xingin.xhs:id/agh");
    etSecond = id("com.xingin.xhs:id/agh");
    etFirst.setText("hongchen123");
    etSecond.setText("hongchen123");
    click("完成");
    sleep(1000);
    back();
    sleep(1000);
    back();
    register();
    // back();
}
// launch();
// toast(currentActivity());
register();
// clickAccount();
// setNewPassword();
// getVerifyCode("15124326339");
// clickLogin();
// clickLoginOut();
// getphone();
// clickSearch();
// clickAttention();
