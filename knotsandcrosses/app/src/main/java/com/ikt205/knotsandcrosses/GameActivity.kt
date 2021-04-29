package com.ikt205.knotsandcrosses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikt205.knotsandcrosses.GameManager.col1
import com.ikt205.knotsandcrosses.GameManager.col2
import com.ikt205.knotsandcrosses.GameManager.col3
import com.ikt205.knotsandcrosses.GameManager.pollState
import com.ikt205.knotsandcrosses.GameManager.contendersTurn
import com.ikt205.knotsandcrosses.databinding.ActivityGameBinding
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

        GameManager.pollGame(this)


//        val intent = Intent(this, PollService::class.java)
//        startService(intent)

        // Sett en if statement inni i hver knapp som sjekker om den er true, så ikke oppdater listen
        binding.btn1.setOnClickListener {
            col1[0] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            println(pollState)
        }

        binding.btn2.setOnClickListener {
            col1[1] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn3.setOnClickListener {
            col1[2] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn4.setOnClickListener {
            col2[0] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn5.setOnClickListener {
            col2[1] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn6.setOnClickListener {
            col2[2] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn7.setOnClickListener {
            col3[0] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)

        }

        binding.btn8.setOnClickListener {
            col3[1] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            println(pollState)
        }

        binding.btn9.setOnClickListener {
            col3[2] = 1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            contendersTurn = true
            GameManager.updateGame(this, pollState)
            disableButtons()
            turnBased()
            print(pollState)
        }

        if (contendersTurn) {
            disableButtons()
            turnBased()
        }
    }

    fun turnBased() {
        GlobalScope.launch {

            while (contendersTurn) {
                delay(5000)
                var tempList = pollState

                GameManager.pollGame(applicationContext)
                delay(1000)

                if (!(tempList.containsAll(pollState) && pollState.containsAll(tempList))) {
                    println("Min tur")
                    enableButtons()
                    contendersTurn = false
                } else {
                    println("Motstanders trekk")
                }
            }
        }
    }

    fun enableButtons() {
        binding.btn1.isClickable = true
        binding.btn2.isClickable = true
        binding.btn3.isClickable = true
        binding.btn4.isClickable = true
        binding.btn5.isClickable = true
        binding.btn6.isClickable = true
        binding.btn7.isClickable = true
        binding.btn8.isClickable = true
        binding.btn9.isClickable = true
    }

    fun disableButtons() {
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