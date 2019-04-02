package com.hc.calling.commands.callingtransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.*;

public class CallingHistory {
    private static Uri callUri = CallLog.Calls.CONTENT_URI;
    private static String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型}

    /**
     * 读取数据
     *
     * @return 读取到的数据
     */
    @SuppressLint("SimpleDateFormat")
    public static List<CallHistoryDTO> getDataList(Context context) {
        // 1.获得ContentResolver
        ContentResolver resolver = context.getContentResolver();
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG);
        // 2.利用ContentResolver的query方法查询通话记录数据库

        // 3.通过Cursor获得数据
        Cursor cursor = resolver.query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        List<CallHistoryDTO> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
          String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
            String typeString = "";
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    //"打入"
                    typeString = "打入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    //"打出"
                    typeString = "打出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    //"未接"
                    typeString = "未接";
                    break;
                default:
                    break;
            }
            String dayString = "";
            if ((Integer.parseInt(dayCurrent)) == (Integer.parseInt(dayRecord))) {
                //今天
                dayString = "今天";
            } else if ((Integer.parseInt(dayCurrent) - 1) == (Integer.parseInt(dayRecord))) {
                //昨天
                dayString = "昨天";
            } else {
                //前天
                dayString = "前天";
            }
            if (name == null){
                name = "未备注联系人";
            }
            CallHistoryDTO callHistoryDTO = new CallHistoryDTO(name,number,date,duration+"",typeString,time);

            list.add(callHistoryDTO);
        }
        return list;
    }

    private static Uri SMS_INBOX = Uri.parse("content://sms/");

    public static List<Map<String, Object>> getSmsFromPhone(Context context) {
        List<Map<String, Object>> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        @SuppressLint("Recycle") Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return null;
        }
        while (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            Map<String, Object> map = new HashMap<>();
            map.put("num", number);
            map.put("mess", body);
            list.add(map);
        }
        return list;
    }

}
