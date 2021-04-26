package com.ikt205.knotsandcrosses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.databinding.ActivityGameBinding

class GameActivity: AppCompatActivity() {

    val TAG: String = "GameActivity"

    var gameState: GameState = mutableListOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
    var row0 = mutableListOf(0, 0, 0)
    var row1 = mutableListOf(0, 0, 0)
    var row2 = mutableListOf(0, 0, 0)

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, PollService::class.java)
        startService(intent)

        binding.btn1.setOnClickListener{
            //gamePollState = gameState

            row0[0]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn2.setOnClickListener{
            row0[1]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn3.setOnClickListener{
            row0[2]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn4.setOnClickListener{
            row1[0]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn5.setOnClickListener{
            row1[1]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn6.setOnClickListener{
            row1[2]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn7.setOnClickListener{
            row2[0]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn8.setOnClickListener{
            row2[1]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            println(gameState)
        }

        binding.btn9.setOnClickListener{
            row2[2]=1
            gameState = listOf(row0) + listOf(row1) + listOf(row2)
            GameManager.updateGame(this, gameState)
            print(gameState)
        }

    }
}