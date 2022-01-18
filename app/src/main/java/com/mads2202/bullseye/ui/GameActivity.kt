package com.mads2202.bullseye.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.app.AlertDialog
import com.mads2202.bullseye.GameHelper
import com.mads2202.bullseye.R
import com.mads2202.bullseye.RoundResultStates
import com.mads2202.bullseye.data.PlayersDao
import com.mads2202.bullseye.databinding.ActivityGameBinding
import com.mads2202.bullseye.ui.MainActivity.Companion.NICKNAME

class GameActivity : AppCompatActivity() {
    companion object {
        private const val ROUND_COUNTER = "ROUND_COUNTER"
        private const val SCORE = "SCORE"
        private const val NUMBER = "NUMBER"
    }

    private var roundCounter = 1
    private lateinit var nickname: String
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(NICKNAME)?.let {
            nickname = it
        }
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews(savedInstanceState)
    }

    private fun setUpViews(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            binding.numberTextView.text = GameHelper.generateNumber().toString()
            binding.score.text = 0.toString()
            binding.round.text = roundCounter.toString()
        } else {
            binding.score.text = savedInstanceState.getInt(SCORE).toString()
            binding.numberTextView.text = savedInstanceState.getInt(NUMBER).toString()
            binding.round.text = savedInstanceState.getInt(ROUND_COUNTER).toString()
        }
        binding.rulesImageButton.setOnClickListener {
            showRulesDialog()
        }
        binding.newGameImageButton.setOnClickListener {
            startNewGame()
        }
        binding.hitButton.setOnClickListener {
            val roundResult = GameHelper.getRoundScore(
                binding.slider.progress,
                binding.numberTextView.text.toString().toInt()
            )
            if (roundResult != RoundResultStates.MISSED.points) {
                AlertDialog.Builder(this).setTitle(resources.getString(R.string.nice_shot))
                    .setMessage("You Choose ${binding.slider.progress} ")
                    .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                        onCorrectAnswer(roundResult)
                        dialog.dismiss()
                    }.show()
            } else {
                showGameOverDialog()
            }
        }
    }

    private fun startNewGame() {
        roundCounter = 1
        binding.score.text = 0.toString()
        binding.round.text = roundCounter.toString()
        binding.numberTextView.text = GameHelper.generateNumber().toString()
        binding.hitButton.isEnabled = true
        binding.slider.progress = 0
    }

    private fun onCorrectAnswer(roundResult: Int) {
        binding.slider.progress = 0
        binding.round.text = (++roundCounter).toString()
        binding.score.text =
            (binding.score.text.toString().toInt() + roundResult).toString()
        binding.numberTextView.text = GameHelper.generateNumber().toString()
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this).setTitle(resources.getString(R.string.game_over))
            .setMessage("You Choose ${binding.slider.progress} Your score: ${binding.score.text}")
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                binding.hitButton.isEnabled = false
                PlayersDao.updatePlayerScore(nickname, binding.score.text.toString().toInt())
                dialog.dismiss()
            }.show()
    }

    private fun showRulesDialog() {
        AlertDialog.Builder(this).setTitle("Rules")
            .setMessage(
                "Выбирете число с помощью слайдера.\n " +
                        "-Если оно совпадает с числом на экране вам начисляется 10 очков.\n" +
                        "  -Если вы промахнулись на 3 и меньше в любую сторону, то вам начисляется 5 очков.\n" +
                        "  -Если вы промахнулись на 5 и меньше в любую сторону, то вам начисляется 1 очко.\n" +
                        "  -Если вы промахнулись сильнее, то игра окончена"
            )
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                binding.hitButton.isEnabled = false
                dialog.dismiss()
            }.show()
    }

    override fun onBackPressed() {
        PlayersDao.updatePlayerScore(nickname, binding.score.text.toString().toInt())
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ROUND_COUNTER, roundCounter)
        outState.putInt(SCORE, binding.score.text.toString().toInt())
        outState.putInt(NUMBER, binding.numberTextView.text.toString().toInt())
    }
}