package com.ikt205.knotsandcrosses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ikt205.knotsandcrosses.GameManager.col1
import com.ikt205.knotsandcrosses.GameManager.col2
import com.ikt205.knotsandcrosses.GameManager.col3
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.pollState
import com.ikt205.knotsandcrosses.databinding.ActivityGameBinding

class GameActivity: AppCompatActivity() {

    val TAG: String = "GameActivity"

    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GameManager.pollGame(this)

        val intent = Intent(this, PollService::class.java)
        startService(intent)


        binding.btn1.setOnClickListener{
            //gamePollState = gameState
            col1[0]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn2.setOnClickListener{
            col1[1]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn3.setOnClickListener{
            col1[2]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn4.setOnClickListener{
            col2[0]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn5.setOnClickListener{
            col2[1]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn6.setOnClickListener{
            col2[2]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn7.setOnClickListener{
            col3[0]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn8.setOnClickListener{
            col3[1]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            println(pollState)
        }

        binding.btn9.setOnClickListener{
            col3[2]=1
            pollState = listOf(col1) + listOf(col2) + listOf(col3)
            GameManager.updateGame(this, pollState)
            print(pollState)
        }

    }
}