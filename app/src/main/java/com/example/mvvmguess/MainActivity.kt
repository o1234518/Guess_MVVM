package com.example.mvvmguess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100
    private lateinit var viewModel: GuessViewModel
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)

        viewModel.counter.observe(this, Observer { data ->
            tv_counter.setText(data.toString())
        })

        viewModel.result.observe(this, Observer { result ->
            val message = when (result) {
                GameResult.BINGO -> getString(R.string.game_result_bingo)
                GameResult.BIGGER -> getString(R.string.game_result_bigger)
                GameResult.SMALLER -> getString(R.string.game_result_smaller)
            }

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.system_message))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), {dialog, which ->
                    if(result == GameResult.BINGO) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("RECORD", viewModel.count)
                        startActivityForResult(intent, REQUEST_CODE)
                    }
                })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //use apply . also
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.system_message))
                    setMessage(getString(R.string.do_you_want_replay))
                    setNegativeButton(getString(R.string.ok), {dialog, which ->
                        viewModel.reset()
                    })
                    setPositiveButton(getString(R.string.cancel), null)
                }.also { builder ->
                    builder.show()
                }

                //original
//                AlertDialog.Builder(this)
//                    .setTitle(getString(R.string.system_message))
//                    .setMessage(getString(R.string.do_you_want_replay))
//                    .setNegativeButton(getString(R.string.ok), {dialog, which ->
//                        viewModel.reset()
//                    })
//                    .setPositiveButton(getString(R.string.cancel), null)
//                    .show()
            }
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