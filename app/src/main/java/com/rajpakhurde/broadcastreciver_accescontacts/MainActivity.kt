package com.rajpakhurde.broadcastreciver_accescontacts

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnGetContacts = findViewById<Button>(R.id.btnGetContacts)
        btnGetContacts.setOnClickListener {
            btnGetContactPressed(it)
        }

    }

    fun btnGetContactPressed(v: View) {
        getPhoneContacts()
    }

    fun getPhoneContacts() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissionList = mutableListOf<String>(Manifest.permission.READ_CONTACTS)
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 0)
        }

        var contentResolver = getContentResolver()
        var uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        var cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        Log.i(TAG, "Total no of contacts : ${cursor!!.count}")

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                Log.i(TAG,"Contact Name: $contactName Phone no: $contactNum")
            }
        }
    }
}