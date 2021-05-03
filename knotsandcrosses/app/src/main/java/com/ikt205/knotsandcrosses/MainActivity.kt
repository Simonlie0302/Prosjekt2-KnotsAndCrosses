package com.ikt205.knotsandcrosses


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.ikt205.knotsandcrosses.GameManager.contenderName
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.gameList
import com.ikt205.knotsandcrosses.GameManager.myName
import com.ikt205.knotsandcrosses.GameManager.opponentList
import com.ikt205.knotsandcrosses.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), GameDialogListener {

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

            // Starting the game activity with ForResualt to further use OneActivityResult functionality
            startActivityForResult(intent, 2)
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
            print("inside alert dialog \n")
            gId = inputGameId.text.toString()
            onDialogJoinGame(inputUsername.text.toString(), inputGameId.text.toString())
            myName = inputUsername.text.toString()
            Thread.sleep(500)
            val intent = Intent(this, GameActivity::class.java)

            // Starting the game activity with ForResualt to further use OneActivityResult functionality
            startActivityForResult(intent, 2)
        }
        builder.show()
    }

    override fun onDialogCreateGame(player: String) {
        GameManager.createGame(player, this)
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        GameManager.joinGame(player, this, gameId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Restart game after win/loos functionality
        contenderName = ""
        gId = ""
        myName = ""
        opponentList = mutableListOf<Int>()
        gameList = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        super.onActivityResult(requestCode, resultCode, data)
    }

}

