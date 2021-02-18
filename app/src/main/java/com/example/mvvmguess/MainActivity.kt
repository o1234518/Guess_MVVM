package com.example.mvvmguess

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var guessNumber: Guess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        guessNumber = Guess()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            guessNumber.refresh()
            tv_counter.text = guessNumber.counter.toString()
        }

        btn_guess_number.setOnClickListener {
            var number = ed_guess_number.text.toString().toInt()
            checkNumber(number)
            guessNumber.counter++
            tv_counter.text = guessNumber.counter.toString()
        }

    }

    fun checkNumber(number:Int){
        var check = guessNumber.secretNumber - number
        var message = ""
        if (check > 0) {
            message = "your number too small"
        } else if (check < 0) {
            message = "your number too big"
        } else if (check == 0) {
            message ="bingo"
        }
        AlertDialog.Builder(this)
            .setTitle("System message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}