package com.ikt205.knotsandcrosses

import android.content.Context
import com.fasterxml.jackson.module.kotlin.*

object GameManager {

    var gId: String = ""
    lateinit var pollState: GameState
    lateinit var col1: MutableList<Int>
    lateinit var col2: MutableList<Int>
    lateinit var col3: MutableList<Int>
    var contendersTurn = true
    var gameList = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    var myState = 1
    var opponentState = 2
    var contenderName = ""
    var myName = ""
    var winMessage = ""

    val StartingGameState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))

    fun createGame(player: String, context: Context) {
        print("Min tur \n")

        contendersTurn = false

        GameService(context).createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
            } else {
                gId = game?.gameId.toString()
                pollGame(context)
                print("\nWe have a game\n")
            }
        }
    }

    fun joinGame(player: String, context: Context, gameId: String) {

        print("Motstanders tur \n")
        gId = gameId

        contendersTurn = true

        GameService(context).joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
            } else {
                pollGame(context)
                print("\nWe have joined a game with the id: " + gameId)
            }
        }
    }

    fun pollGame(context: Context) {

        print("Pollgame function \n")

        GameService(context).pollGame(gId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
            } else {
                println("\nJoined a game with the id: " + gId)

                val gamePollState = game?.state
                val mapper = jacksonObjectMapper()

                var newState = mapper.readValue(gamePollState, List::class.java)

                pollState = newState as GameState

                var newCol1 = newState[0].toMutableList()
                var newCol2 = newState[1].toMutableList()
                var newCol3 = newState[2].toMutableList()

                col1 = newCol1
                col2 = newCol2
                col3 = newCol3

//                var checkContendersData = (col1 + col2 + col3)
//
//                for (i in checkContendersData) {
//                    if (i != myState) {
//                        opponentState = 2
//                    }
//                }

                if (contenderName == "") {
                    if (game?.players?.size!! > 1) {
                        contenderName = game.players[1]
                        println(contenderName)
                    }
                }
                disableContendersButtons()
            }
        }
    }

    fun updateGame(context: Context, updateState: GameState) {

        print("Joining a game function \n")

        GameService(context).updateGame(gId, updateState) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
            } else {
                print("\nWe have joined a game with the id: " + gId)
            }
        }
    }

    fun gameFinished(state: Int): Boolean {
        winMessage = if (state == myState) {
            "I won"
        } else {
            "Opponent win"
        }

        // Horisontal win
        if (col1[0] == state && col1[1] == state && col1[2] == state) {
            println(winMessage)
            return true
        }
        if (col2[0] == state && col2[1] == state && col2[2] == state) {
            println(winMessage)
            return true
        }
        if (col3[0] == state && col3[1] == state && col3[2] == state) {
            println(winMessage)
            return true
        }

        // Vertical win
        if (col1[0] == state && col2[0] == state && col3[0] == state) {
            println(winMessage)
            return true
        }
        if (col1[1] == state && col2[1] == state && col3[1] == state) {
            println(winMessage)
            return true
        }
        if (col1[2] == state && col2[2] == state && col3[2] == state) {
            println(winMessage)
            return true
        }

        // Diagonal win
        if (col1[0] == state && col2[1] == state && col3[2] == state) {
            println(winMessage)
            return true
        }
        if (col1[2] == state && col2[1] == state && col3[0] == state) {
            println(winMessage)
            return true
        }
        return false
    }

    fun disableContendersButtons() {
        if (col1[0] == opponentState) {
            gameList.remove(1)
        }
        if (col1[1] == opponentState) {
            gameList.remove(2)
        }
        if (col1[2] == opponentState) {
            gameList.remove(3)
        }
        if (col2[0] == opponentState) {
            gameList.remove(4)
        }
        if (col2[1] == opponentState) {
            gameList.remove(5)
        }
        if (col2[2] == opponentState) {
            gameList.remove(6)
        }
        if (col3[0] == opponentState) {
            gameList.remove(7)
        }
        if (col3[1] == opponentState) {
            gameList.remove(8)
        }
        if (col3[2] == opponentState) {
            gameList.remove(9)
        }
        println("Denne er ny: " + gameList)
    }

}