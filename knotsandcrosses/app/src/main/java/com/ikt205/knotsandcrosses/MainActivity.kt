package com.ikt205.knotsandcrosses

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.ikt205.knotsandcrosses.App.Companion.context
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.myName
import com.ikt205.knotsandcrosses.GameManager.pollGame
import com.ikt205.knotsandcrosses.databinding.ActivityMainBinding
import com.ikt205.knotsandcrosses.databinding.DialogCreateGameBinding
import com.ikt205.knotsandcrosses.databinding.DialogJoinGameBinding
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(), GameDialogListener {

    val TAG: String = "MainActivity"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGameButton.setOnClickListener {
            createNewGame()
        }

        binding.joinGameButton.setOnClickListener {
            joinGame()
        }
    }

    private fun createNewGame() {
        //val dlg = CreateGameDialog()

        //dlg.show(supportFragmentManager,"CreateGameDialogFragment")

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Create a game:")
        val dialogLayout = inflater.inflate(R.layout.dialog_create_game, null)
        val inputText = dialogLayout.findViewById<EditText>(R.id.username)
        builder.setView(dialogLayout)

        builder.setPositiveButton("OK") { dialogInterface, i ->
            onDialogCreateGame(inputText.text.toString())
            myName = inputText.text.toString()
            Thread.sleep(500)
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }

    private fun joinGame() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Join a game:")
        val dialogLayout = inflater.inflate(R.layout.dialog_join_game, null)
        val inputUsername = dialogLayout.findViewById<EditText>(R.id.username)
        val inputGameId = dialogLayout.findViewById<EditText>(R.id.gameId)
        builder.setView(dialogLayout)
        print("before alert dialog \n")

        builder.setPositiveButton("OK") { dialogInterface, i ->
            //listener.onDialogCreateGame(inputTex t.text.toString())
            print("inside alert dialog \n")
            gId = inputGameId.text.toString()
            onDialogJoinGame(inputUsername.text.toString(), inputGameId.text.toString())
            Thread.sleep(500)
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        builder.show()
    }

    override fun onDialogCreateGame(player: String) {
        GameManager.createGame(player, this)
        Log.d(TAG, player)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        GameManager.joinGame(player, this, gameId)
        Log.d(TAG, "$player $gameId")
    }

}