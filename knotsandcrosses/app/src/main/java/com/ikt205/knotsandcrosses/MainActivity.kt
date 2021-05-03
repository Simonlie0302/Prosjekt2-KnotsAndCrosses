package com.ikt205.knotsandcrosses

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.ikt205.knotsandcrosses.GameManager.contenderName
import com.ikt205.knotsandcrosses.GameManager.gId
import com.ikt205.knotsandcrosses.GameManager.gameList
import com.ikt205.knotsandcrosses.GameManager.myName
import com.ikt205.knotsandcrosses.GameManager.opponentList
import com.ikt205.knotsandcrosses.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


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

        checkTheme()
        btn_change_theme.setOnClickListener { chooseThemeDialog() }
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
    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        // Dialog pop up with 3 different choices
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            // Using the built in android studio .xml night theme, with manually set colors
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }
}

class MyPreferences(context: Context?) {

    companion object {
        private const val DARK_STATUS = ""
    }

    /*
    Tried to use the default shared preference to track the phone default value, could not test
    this because i did not find any dark mode option on my emulator. I did install the gradle import but its still acting weird.
    */
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
}

