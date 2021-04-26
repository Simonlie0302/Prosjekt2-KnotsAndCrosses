package com.ikt205.knotsandcrosses

import android.content.Context
import java.util.*
import kotlin.concurrent.schedule
import com.google.gson.*


object GameManager {

    var player: String? = null
    var state: GameState? = null
    var gId = ""
    //var gamePollState = ""

    val StartingGameState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))

    fun createGame(player: String, context: Context) {
        print("Created game function \n")

        GameService(context).createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                gId = game?.gameId.toString()
                print("\nWe have a game\n")
            }
        }
    }

    fun joinGame(player: String, context: Context, gameId: String) {

        print("Joining a game function \n")

        GameService(context).joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                print("\nWe have joined a game with the id: " + gameId)
            }
        }
    }

    fun pollGame(context: Context) {

        print("Pollgame function \n")

        GameService(context).pollGame(gId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                print("\nWe have joined a game with the id: " + gId)

                val gamePollState = game?.state

                val gson = Gson()

                val objectList = gson.fromJson(gamePollState, Array::class.java).asList()

                println("\n her er listen" + objectList + "Ny liste")

            }
        }
    }

    fun updateGame(context: Context, updateState:GameState) {

        print("Joining a game function \n")

        GameService(context).updateGame(gId, updateState) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                print("\nWe have joined a game with the id: " + gId)
            }
        }
    }
}