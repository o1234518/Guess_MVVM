package com.example.mvvmguess

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GuessViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)

        viewModel.counter.observe(this, Observer { data ->
            tv_counter.setText(data.toString())
        })

        viewModel.result.observe(this, Observer { result ->
            val message = when(result) {
                GameResult.BINGO -> "Bingo!!"
                GameResult.BIGGER -> "Your number too small."
                GameResult.SMALLER -> "Your number too big."
            }

            AlertDialog.Builder(this)
                .setTitle("System message")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            viewModel.reset()
        }

        btn_guess_number.setOnClickListener {
            var number = ed_guess_number.text.toString().toInt()
            viewModel.guess(number)
        }

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