package com.ikt205.knotsandcrosses

import android.content.Context
import com.google.gson.*
import java.util.*
import com.fasterxml.jackson.module.kotlin.*


object GameManager {

    var player: String? = null
    var state: GameState? = null
    lateinit var gId:String
    lateinit var pollState:GameState
    lateinit var col1:MutableList<Int>
    lateinit var col2:MutableList<Int>
    lateinit var col3:MutableList<Int>


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
        println(gId)

        GameService(context).pollGame(gId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
                ///TODO("What is the error code? 406 you forgot something in the header. 500 the server di not like what you gave it")
            } else {
                println("\nJoined a game with the id: " + gId)

                val gamePollState = game?.state

//                val gamePollState = Gson().fromJson(it.toString(0), Game::class.java)
//
//                val gson = Gson()
//
//                val objectList = gson.fromJson(gamePollState, Array::class.java).asList()

                val mapper = jacksonObjectMapper()
//
//                val genres = mapper.readValue<List<Int>>(gamePollState.toString())
//
                var newState = mapper.readValue(gamePollState, List::class.java)
//
//                //println("Dette er genres: $genres")
               // println("Dette er dtos: $newState dette er riktig"+newState[0])


                pollState = newState as GameState

                var testState1 = newState[0].toMutableList()
                var testState2 = newState[1].toMutableList()
                var testState3 = newState[2].toMutableList()

                col1 = testState1
                col2 = testState2
                col3 = testState3

//                println("Dette er dtos: $pollState dette er riktig"+pollState[0])


//                println("\n her er listen" + objectList + "Ny liste")

            }
        }
    }

    fun updateGame(context: Context, updateState: GameState) {

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