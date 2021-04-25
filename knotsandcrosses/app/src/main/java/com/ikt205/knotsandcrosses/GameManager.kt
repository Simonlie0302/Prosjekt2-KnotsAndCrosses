package com.ikt205.knotsandcrosses

import android.content.Context

object GameManager {

    var player: String? = null
    var state: GameState? = null

    val StartingGameState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))

    fun createGame(player: String, context: Context) {

        print("Created game function \n")

        GameService(context).createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                print("We have a game!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n")
            }
        }

    }

}