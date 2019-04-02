package com.hc.calling.commands.contact

import android.content.Context
import com.google.gson.Gson
import com.hc.calling.commands.Command
import com.hc.calling.commands.Executor
import com.hc.calling.commands.contact.util.ContactGainer
import com.orhanobut.logger.Logger

/**
 * Created by ChanHong on 2019/3/22
 *
 */
class Contacts(context: Context) : Command(), Executor {
    var context: Context? = null

    init {
        this.context = context
    }

    override fun execute(data: Array<Any>) {



        val contactsList = ContactGainer.getContacts(context!!)
        val data = Gson().toJson(contactsList)
        Logger.json(data)
        emitData(CONTACTS_LIST, data)
    }

    companion object {
        const val CONTACTS_LIST = "contacts_list"
        const val SEND_CONTACTS_LIST = "send_contacts_list"
    }


    data class SmsDTO(
        var address: String,
        var content: String
    )
}