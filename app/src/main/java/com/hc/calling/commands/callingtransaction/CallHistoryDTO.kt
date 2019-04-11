package com.hc.calling.commands.callingtransaction

/**
 * Created by ChanHong on 2019/3/25
 */
data class CallHistoryDTO(
    var name:String,//姓名
    var number:String, //手机号
    var date:String, //通话日期
    var duration:String, //通话时长
    var type:String,//呼入/呼出
    var time:String//通话时间
)