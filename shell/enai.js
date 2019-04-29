const EnAiPackageName = "com.xiaoenai.app";
auto();
let count = 0;
let user1 = ["user1", "password"];
let user2 = ["user2", "password"];
runDingTalk = function () {
    device.wakeUp();
    app.launch(DingTalkPackageName);
    waitForActivity("com.xiaoenai.app.presentation.home.view.activity.HomeActivity", 200)
    click("蜜语"); //点击前往聊天界面
    waitForActivity("com.xiaoenai.app.classes.chat.ChatActivity", 200);
    let et = id("com.xiaoenai.app:id/et_chat_input");
    et.setText("hi I am a rebot"); //打字

    let btn = id("com.xiaoenai.app:id/btn_send");
    btn.click(); //发送
    sleep(2000); //等等再回主页
    back();
    click("我"); //准备切换账号
    sleep(1000);
    click("设置");
    waitForActivity("com.xiaoenai.app.classes.settings.SettingsActivity", 200);
    let btnLoginOut = id("com.xiaoenai.app:id/SettingLayoutExit");
    btnLoginOut.click(); //退出登录
    let btnConfirm = id("com.xiaoenai.app:id/tv_confirm");
    btnConfirm.click(); //等他的alert跳出来再等确定
    switchAccount(); //切换账号 然后再运行一边上面的代码

}

switchAccount = function () {
    waitForActivity("com.mzd.feature.account.view.activity.AccountActivity", 200);
    let btnLogin = id("com.xiaoenai.app:id/bt_login");
    btnLogin.click();

    let etUser = id("com.xiaoenai.app:id/edit_username");
    let etPassword = id("com.xiaoenai.app:id/edit_passwd");

    if (count == 0) {
        etUser.setText(user1[0]);
        etPassword.setText(user1[1]);
        count = 1;
    } else {
        etUser.setText(user2[0]);
        etPassword.setText(user2[1]);
        count = 2;
    }

    let buttonConfirm = id("com.xiaoenai.app:id/bt_login");
    buttonConfirm.click();

    runDingTalk();
    if (count == 2) {
        return;
    }

}

runDingTalk();