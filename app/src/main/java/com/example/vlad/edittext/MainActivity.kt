package com.example.vlad.edittext

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where


class MainActivity : AppCompatActivity() {

    internal var posts: MutableList<DbList>? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val RealmDb: DbList

        setContentView(R.layout.activity_main)
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
        
        val sendKey = findViewById<Button>(R.id.sendKey_)
        sendKey.setOnClickListener {
          setContentView(R.layout.activity_main)
            val receiver = findViewById<EditText>(R.id.editText1_)
            val message = findViewById<EditText>(R.id.editText2_)
            val button = findViewById<Button>(R.id.button)
            val getMessages=findViewById<TextView>(R.id.GetTextView)
            button.setOnClickListener {

                ServerJob.sendMessage(message.text.toString(), receiver.text.toString())

            }
            //  startActivity(R.layout.activity_main)
        }
        val getKey = findViewById<Button>(R.id.getKey)
        getKey.setOnClickListener {
          setContentView(R.layout.activity_get)
            val receiver = findViewById<EditText>(R.id.editText1)
            val message = findViewById<EditText>(R.id.editText2)
            val button = findViewById<Button>(R.id.button)
            val getMessages=findViewById<TextView>(R.id.GetTextView)
            val button2=findViewById<Button>(R.id.GetButtonGet)
            //val mArray = findViewById<Array<DbList>>(R.array.metrics)

            button2.setOnClickListener{


                ServerJob.getMessages(receiver.text.toString(),getMessages)

                val realm = Realm.getDefaultInstance() // opens "myrealm.realm"
                try {
                    realm.beginTransaction()
                    realm.copyToRealmOrUpdate(this@MainActivity.posts) //Запись в BD переменной пост
                  /*  val dogs = realm
                             .where<DbList>()
                             .findAll()
                             .asFlowable()

*/
                    // Log.i("Id", dogs.)

                    realm.commitTransaction()
                    // realm.executeTransaction()
                } finally {
                    realm.close()
                }


            }
            //  startActivity(R.layout.activity_main)
        }
        val usingKey = findViewById<Button>(R.id.usingKey)
        usingKey.setOnClickListener {
          setContentView(R.layout.activity_using)


            val intent = Intent(this@MainActivity, NFCActivity::class.java)
            startActivity(intent)
            //  startActivity(R.layout.activity_main)
        }

        
/*
        val receiver = findViewById<EditText>(R.id.editText1)
        val message = findViewById<EditText>(R.id.editText2)
        val button = findViewById<Button>(R.id.button)
        val getMessages=findViewById<TextView>(R.id.GetTextView)
        button.setOnClickListener {
            ServerJob.sendMessage(message.text.toString(), receiver.text.toString())

        }
        val button2=findViewById<Button>(R.id.GetButtonGet)
        button2.setOnClickListener{
            ServerJob.getMessages(receiver.text.toString(),getMessages)
        }
*/
    }

}
