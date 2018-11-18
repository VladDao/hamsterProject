package com.example.vlad.edittext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

@SuppressLint("Registered")
class NFCActivity : Activity(), NfcAdapter.CreateNdefMessageCallback {

    private var mNfcAdapter: NfcAdapter? = null
    private lateinit var textView: TextView;


    override fun createNdefMessage(event: NfcEvent?): NdefMessage {
        val text = "Beam me up, Android!\n\n" +
                "Beam Time: " + System.currentTimeMillis()
        return NdefMessage(
            arrayOf(
                NdefRecord.createMime("application/vnd.com.example.android.beam", text.toByteArray())
            )
        )
    }

    val TAG = "MainActivity"

    val FRAGTAG = "BeamLargeFilesFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_authorization)

        //val textView = findViewById<TextView>(R.id.output)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        mNfcAdapter?.setNdefPushMessageCallback(this, this)

        // val transaction = supportFragmentManager.beginTransaction()
        //val fragment = BeamLargeFilesFragment()
        //transaction.add(fragment, FRAGTAG)
        //transaction.commit()
    }


    override fun onResume() {
        super.onResume()

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            processIntent(intent)
        }
    }
    override fun onNewIntent(intent: Intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        //  textView = findViewById(R.id.output)
        // only one message sent during the beam
        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMsgs ->
            (rawMsgs[0] as NdefMessage).apply {
                // record 0 contains the MIME type, record 1 is the AAR, if present
                textView.text = String(records[0].payload)
            }
        }
    }

}