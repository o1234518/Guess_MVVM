package com.example.mvvmguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val record = intent.getIntExtra("RECORD", -1)

        tv_record.setText(record.toString())

        btn_record_save.setOnClickListener { view ->
            var user_name = ed_user_name.text.toString()
            if(user_name.equals("")) {
                user_name = "Guess"
            }
//            getSharedPreferences("Guess", MODE_PRIVATE)
//                .edit()
//                .putInt("REC_COUNTER", record)
//                .putString("REC_USER", user_name)
//                .apply()
            getSharedPreferences("Guess", MODE_PRIVATE).apply {
                edit()
                    .apply{
                        putInt("REC_COUNTER", record)
                        putString("REC_USER", user_name)
                    }.also { editor ->
                        editor.apply()
                    }
            }
            setResult(RESULT_OK)
            finish()
        }

    }
}