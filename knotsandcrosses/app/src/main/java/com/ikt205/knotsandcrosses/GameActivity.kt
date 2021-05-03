package com.ikt205.knotsandcrosses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import com.ikt205.knotsandcrosses.GameManager.col1
import com.ikt205.knotsandcrosses.GameManager.col2
import com.ikt205.knotsandcrosses.GameManager.col3
import com.ikt205.knotsandcrosses.GameManager.contenderName
import com.ikt205.knotsandcrosses.GameManager.pollState
import com.ikt205.knotsandcrosses.GameManager.contendersTurn
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.gameFinished
import com.ikt205.knotsandcrosses.GameManager.gameList
import com.ikt205.knotsandcrosses.GameManager.myName
import com.ikt205.knotsandcrosses.GameManager.myState
import com.ikt205.knotsandcrosses.GameManager.opponentList
import com.ikt205.knotsandcrosses.GameManager.opponentState
import com.ikt205.knotsandcrosses.GameManager.pollGame
import com.ikt205.knotsandcrosses.databinding.ActivityGameBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Because I used the altering toolbar functionality in my last project, i decided to not do that here
        binding.gameTitleID.text = gId
        binding.contenderGameName.text = "Waiting for opponent..."
        binding.myGameName.text = myName

        binding.btn1.setOnClickListener {
            col1[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(1)
            binding.btn1.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn2.setOnClickListener {
            col1[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(2)
            binding.btn2.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn3.setOnClickListener {
            col1[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(3)
            binding.btn3.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn4.setOnClickListener {
            col2[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(4)
            binding.btn4.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn5.setOnClickListener {
            col2[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(5)
            binding.btn5.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn6.setOnClickListener {
            col2[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(6)
            binding.btn6.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn7.setOnClickListener {
            col3[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(7)
            binding.btn7.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn8.setOnClickListener {
            col3[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(8)
            binding.btn8.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        binding.btn9.setOnClickListener {
            col3[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(9)
            binding.btn9.setBackgroundResource(R.drawable.ic_baseline_close_24)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
        }

        if (contendersTurn) {
            disableButtons()
            turnBased()
        }

        GlobalScope.launch {
            while (binding.contenderGameName.text == "Waiting for opponent...") {
                delay(3000)
                pollGame(applicationContext)

                runOnUiThread {
                    if (contenderName != "") {
                        binding.contenderGameName.text = contenderName
                    }
                }
            }
        }
    }

    fun turnBased() {
        GlobalScope.launch {
            while (contendersTurn) {
                delay(1000)
                if(gameFinished(myState)){
                    contendersTurn = false
                    runOnUiThread {
                        binding.winOrLose.text = GameManager.winMessage
                    }

                    delay(5000)
                    // Finish the activity and go to mainctivity onActivityResult
                    finish()
                }
                var tempList = pollState
                delay(2000)

                pollGame(applicationContext)

                delay(1000)

                if (!(tempList.containsAll(pollState) && pollState.containsAll(tempList))) {
                    contendersTurn = false

                    if(gameFinished(opponentState)){
                        contendersTurn = false
                        runOnUiThread {
                            binding.winOrLose.text = GameManager.winMessage
                        }
                        delay(5000)
                        // Finish the activity and go to mainctivity onActivityResult
                        finish()
                    }
                    enableButtons()
                }
            }
        }
    }

    fun enableButtons() {
        runOnUiThread {
            for (btn in gameList) {
                if (btn == 1) {
                    binding.btn1.isClickable = true
                }
                if (btn == 2) {
                    binding.btn2.isClickable = true
                }
                if (btn == 3) {
                    binding.btn3.isClickable = true
                }
                if (btn == 4) {
                    binding.btn4.isClickable = true
                }
                if (btn == 5) {
                    binding.btn5.isClickable = true
                }
                if (btn == 6) {
                    binding.btn6.isClickable = true
                }
                if (btn == 7) {
                    binding.btn7.isClickable = true
                }
                if (btn == 8) {
                    binding.btn8.isClickable = true
                }
                if (btn == 9) {
                    binding.btn9.isClickable = true
                }
            }
            for (btn in opponentList) {
                if (btn == 1) {
                    binding.btn1.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 2) {
                    binding.btn2.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 3) {
                    binding.btn3.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 4) {
                    binding.btn4.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 5) {
                    binding.btn5.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 6) {
                    binding.btn6.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 7) {
                    binding.btn7.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 8) {
                    binding.btn8.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
                if (btn == 9) {
                    binding.btn9.setBackgroundResource(R.drawable.ic_baseline_panorama_fish_eye_24)
                }
            }
        }
    }

    fun disableButtons() {
        runOnUiThread {
            binding.btn1.isClickable = false
            binding.btn2.isClickable = false
            binding.btn3.isClickable = false
            binding.btn4.isClickable = false
            binding.btn5.isClickable = false
            binding.btn6.isClickable = false
            binding.btn7.isClickable = false
            binding.btn8.isClickable = false
            binding.btn9.isClickable = false
        }
    }
}