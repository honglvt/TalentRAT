package com.hc.calling.commands.callingtransaction

/**
 * Created by ChanHong on 2019/3/25
 *  map.put("name", (name == null) ? "未备注联系人" : name);
map.put("number", number);//手机号
map.put("date", date);//通话日期
// "分钟"
map.put("duration", (duration / 60) + "分钟");//时长
map.put("type", typeString);//类型
map.put("time", time);//通话时间
 */
data class CallHistoryDTO(
    var name:String,//姓名
    var number:String, //手机号
    var date:String, //通话日期
    var duration:String, //通话时长
    var type:String,//呼入/呼出
    var time:String//通话时间
)