package com.example.smsreader


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var smsRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //smsDisplayTextView = findViewById(R.id.smsDisplayTextView)

        smsRecyclerView = findViewById(R.id.smsRecyclerView)
        smsRecyclerView.layoutManager = LinearLayoutManager(this)


        checkAndRequestPermissions()
        //getDeviceInfo(this)


    }


    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), 101)
        } else {
            loadSmsMessages()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSmsMessages()
        }
    }


    private fun loadSmsMessages() {
        val uriSms = Uri.parse("content://sms/inbox")
        val cursor = contentResolver.query(uriSms, null, null, null, null)
        val smsList = mutableListOf<SmsMessage>()

        cursor?.use {
            val indexAddress = it.getColumnIndex("address")
            val indexBody = it.getColumnIndex("body")
            while (it.moveToNext()) {
                val address = it.getString(indexAddress)  // Phone number
                val body = it.getString(indexBody)
                smsList.add(SmsMessage(address, body))
            }
        }

        smsRecyclerView.adapter = SmsAdapter(smsList)
    }


    data class SmsMessage(val address: String, val body: String)


    /*
    private fun checkAndRequestPermissions() {
        val permissionsNeeded = listOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.GET_ACCOUNTS
        )

        val permissionsToRequest = permissionsNeeded.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 101)
        }
    }


    private fun getDeviceInfo(context: Context) {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simSerialNumber = telephonyManager.simSerialNumber

        val accountManager = AccountManager.get(context)
        val accounts = accountManager.getAccountsByType("com.google")
        val accountName = accounts.firstOrNull()?.name ?: "No Google Account"

        // Update the TextView with the information
        val infoTextView = findViewById<TextView>(R.id.infoTextView)
        infoTextView.text = "SIM Serial: $simSerialNumber\nGoogle Account: $accountName"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty()) {
            val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (!allPermissionsGranted) {
                // Handle the case where permissions are not granted

                // Inform the user that all permissions are necessary for this app
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    showDialog("These permissions are essential for the app to function properly. Please grant them.")
                } else {
                    // No explanation needed; we can request the permissions again
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.GET_ACCOUNTS),
                        101
                    )
                }

            }
        }
    }


    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                checkAndRequestPermissions()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
*/
}