package com.ikt205.knotsandcrosses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.ikt205.knotsandcrosses.databinding.ActivityMainBinding
import com.ikt205.knotsandcrosses.databinding.DialogCreateGameBinding

class MainActivity : AppCompatActivity() , GameDialogListener {

    val TAG:String = "MainActivity"

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

    private fun createNewGame(){
        //val dlg = CreateGameDialog()

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Enter name of list")
        val dialogLayout = inflater.inflate(R.layout.dialog_create_game, null)
        val inputText = dialogLayout.findViewById<EditText>(R.id.username)
        builder.setView(dialogLayout)
        print("before alert dialog \n")

        // Using the dialogInterface to retrieve the input text and further call to the
        // function addTodo with the properties inputText and mutableListof
        builder.setPositiveButton("OK") { dialogInterface, i ->
            //listener.onDialogCreateGame(inputText.text.toString())
            print("inside alert dialog \n")
            onDialogCreateGame(inputText.text.toString())
        }
        builder.show()

        print("after alert dialog \n")

        //dlg.show(supportFragmentManager,"CreateGameDialogFragment")
    }

    private fun joinGame(){

    }

    override fun onDialogCreateGame(player: String) {
        GameManager.createGame(player, this)
        Log.d(TAG,player)
    }

//    override fun onDialogJoinGame(player: String, gameId: String) {
//        Log.d(TAG, "$player $gameId")
//    }

}