package com.ikt205.knotsandcrosses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikt205.knotsandcrosses.GameManager.col1
import com.ikt205.knotsandcrosses.GameManager.col2
import com.ikt205.knotsandcrosses.GameManager.col3
import com.ikt205.knotsandcrosses.GameManager.contenderName
import com.ikt205.knotsandcrosses.GameManager.pollState
import com.ikt205.knotsandcrosses.GameManager.contendersTurn
import com.ikt205.knotsandcrosses.GameManager.createGame
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.gameFinished
import com.ikt205.knotsandcrosses.GameManager.gameList
import com.ikt205.knotsandcrosses.GameManager.myName
import com.ikt205.knotsandcrosses.GameManager.myState
import com.ikt205.knotsandcrosses.GameManager.opponentState
import com.ikt205.knotsandcrosses.GameManager.pollGame
import com.ikt205.knotsandcrosses.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {

    val TAG: String = "GameActivity"

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gameTitleID.text = gId

        // Sett en if statement inni i hver knapp som sjekker om den er true, så ikke oppdater listen
        binding.btn1.setOnClickListener {
            col1[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(1)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(gameList)
        }

        binding.btn2.setOnClickListener {
            col1[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(2)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn3.setOnClickListener {
            col1[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(3)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn4.setOnClickListener {
            col2[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(4)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn5.setOnClickListener {
            col2[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(5)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn6.setOnClickListener {
            col2[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(6)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn7.setOnClickListener {
            col3[0] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(7)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)

        }

        binding.btn8.setOnClickListener {
            col3[1] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(8)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn9.setOnClickListener {
            col3[2] = myState
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            gameList.remove(9)
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            print(pollState)
        }

        if (contendersTurn) {
            disableButtons()
            turnBased()
        }

        binding.contenderGameName.text = "Waiting for opponent..."
        binding.myGameName.text = myName

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
                    finish()
                }
                var tempList = pollState
                delay(3000)

                pollGame(applicationContext)

                delay(1000)

                if (!(tempList.containsAll(pollState) && pollState.containsAll(tempList))) {
                    println("Min tur")
                    contendersTurn = false

                    if(gameFinished(opponentState)){
                        contendersTurn = false
                        runOnUiThread {
                            binding.winOrLose.text = GameManager.winMessage
                        }
                        delay(5000)
                        finish()
                    }
                    enableButtons()
                } else {
                    println("Motstanders trekk")
                }
            }
        }
    }

    fun enableButtons() {
        runOnUiThread {
            for (btn in gameList) {
                if (btn == 1) {
                    binding.btn1.isEnabled = true
                }
                if (btn == 2) {
                    binding.btn2.isEnabled = true
                }
                if (btn == 3) {
                    binding.btn3.isEnabled = true
                }
                if (btn == 4) {
                    binding.btn4.isEnabled = true
                }
                if (btn == 5) {
                    binding.btn5.isEnabled = true
                }
                if (btn == 6) {
                    binding.btn6.isEnabled = true
                }
                if (btn == 7) {
                    binding.btn7.isEnabled = true
                }
                if (btn == 8) {
                    binding.btn8.isEnabled = true
                }
                if (btn == 9) {
                    binding.btn9.isEnabled = true
                }
            }
        }
    }

    fun disableButtons() {
        runOnUiThread {
            binding.btn1.isEnabled = false
            binding.btn2.isEnabled = false
            binding.btn3.isEnabled = false
            binding.btn4.isEnabled = false
            binding.btn5.isEnabled = false
            binding.btn6.isEnabled = false
            binding.btn7.isEnabled = false
            binding.btn8.isEnabled = false
            binding.btn9.isEnabled = false
        }
    }
}