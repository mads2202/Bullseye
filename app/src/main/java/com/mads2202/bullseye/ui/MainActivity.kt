package com.mads2202.bullseye.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.mads2202.bullseye.R
import com.mads2202.bullseye.data.PlayersDao

class MainActivity : AppCompatActivity() {
    companion object {
        val NICKNAME: String = "NICKNAME"
    }

    private lateinit var exitButton: Button
    private lateinit var recordsButton: Button
    private lateinit var newGameButton: Button

    private val mainActivityTag = "Main Activity Tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
        Log.d(mainActivityTag, "onCreate")
    }

    private fun setUpViews() {
        exitButton = findViewById(R.id.exit_button)
        exitButton.setOnClickListener {
            finish()
        }
        recordsButton = findViewById(R.id.records_button)
        recordsButton.setOnClickListener {
            val intent = Intent(this, RecordsActivity::class.java)
            startActivity(intent)
        }
        newGameButton = findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener {
            createNicknamePickerDialog()
        }
    }

    private fun createNicknamePickerDialog() {
        val enterNameLayout = layoutInflater.inflate(R.layout.enter_name_dialog, null)
        AlertDialog.Builder(this).setView(enterNameLayout)
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                val nameField: TextInputEditText =
                    enterNameLayout.findViewById(R.id.name_input_edit_text)
                nameField.text?.let {
                    if (it.isNotEmpty()) {
                        PlayersDao.addPlayer(it.toString())
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra(NICKNAME, it.toString())
                        startActivity(intent)
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    override fun onStart() {
        super.onStart()
        Log.d(mainActivityTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(mainActivityTag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(mainActivityTag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(mainActivityTag, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(mainActivityTag, "onDestroy")
    }
}