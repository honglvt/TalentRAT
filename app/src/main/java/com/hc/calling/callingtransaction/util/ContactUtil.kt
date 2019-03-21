package com.hc.calling.callingtransaction.util

import android.content.Context
import android.provider.ContactsContract

class ContactUtil {
    companion object {

        fun getContact(context: Context): MutableList<MutableMap<String, String>> {
            var contacts = mutableListOf<MutableMap<String, String>>()
            var uri = ContactsContract.Contacts.CONTENT_URI

            var projection = arrayOf(
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME
            )

            var cursor = context.contentResolver.query(uri, projection, null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val map = mutableMapOf<String, String>()
                    val id = cursor.getLong(0)
                    val name = cursor.getString(1)

                    val phoneProjection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    val phoneCursor = context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            phoneProjection,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                            null,
                            null
                    )

                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            val phoneNum = phoneCursor.getString(0)
                            map[name] = phoneNum
                            contacts.add(map)
                        } while (phoneCursor.moveToNext())
                    }

                } while (cursor.moveToNext())
            }
            return contacts
        }
    }
}