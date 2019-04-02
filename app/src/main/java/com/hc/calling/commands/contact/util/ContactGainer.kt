package com.hc.calling.commands.contact.util

import android.content.Context
import android.provider.ContactsContract
import com.hc.calling.commands.contact.util.ContactDTO

class ContactGainer {
    companion object {

        fun getContacts(context: Context): MutableList<ContactDTO> {
            val contacts = mutableListOf<ContactDTO>()
            val uri = ContactsContract.Contacts.CONTENT_URI

            val projection = arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
            )

            val cursor = context.contentResolver.query(uri, projection, null, null, null)

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
                            val contact = ContactDTO(name, phoneNum)
                            contacts.add(contact)
                        } while (phoneCursor.moveToNext())
                    }

                } while (cursor.moveToNext())
            }
            return contacts
        }
    }
}