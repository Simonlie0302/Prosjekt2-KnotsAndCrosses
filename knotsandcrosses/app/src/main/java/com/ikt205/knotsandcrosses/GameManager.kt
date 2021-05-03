package com.ikt205.knotsandcrosses

import android.content.Context
import com.fasterxml.jackson.module.kotlin.*

object GameManager {

    lateinit var pollState: GameState
    lateinit var col1: MutableList<Int>
    lateinit var col2: MutableList<Int>
    lateinit var col3: MutableList<Int>

    var gameList = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val StartingGameState: GameState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
    var opponentList = mutableListOf<Int>()

    var myState = 1
    var opponentState = 2
    var contenderName = ""
    var myName = ""
    var winMessage = ""
    var gId: String = ""
    var contendersTurn = true


    fun createGame(player: String, context: Context) {

        contendersTurn = false
        // Gameservice function createGame retrieves the callback (game: Game?, err: Int?), returns error with a networkResponse
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

        gId = gameId
        contendersTurn = true

        // Gameservice function createGame retrieves the callback (game: Game?, err: Int?), returns error with a networkResponse
        GameService(context).joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                print("ERROR \n")
            } else {
                pollGame(context)
                contenderName = game?.players?.get(0).toString()
                print("\nWe have joined a game with the id: " + gameId)
            }
        }
    }

    fun pollGame(context: Context) {

        // Gameservice function createGame retrieves the callback (game: Game?, err: Int?), returns error with a networkResponse
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

        // Gameservice function createGame retrieves the callback (game: Game?, err: Int?), returns error with networkResponse
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
            // Adds to the newly set opponent list for usage with O and X
            opponentList.add(1)
            // removes from list to make buttons unclickable
            gameList.remove(1)
        }
        if (col1[1] == opponentState) {
            opponentList.add(2)
            gameList.remove(2)
        }
        if (col1[2] == opponentState) {
            opponentList.add(3)
            gameList.remove(3)
        }
        if (col2[0] == opponentState) {
            opponentList.add(4)
            gameList.remove(4)
        }
        if (col2[1] == opponentState) {
            opponentList.add(5)
            gameList.remove(5)
        }
        if (col2[2] == opponentState) {
            opponentList.add(6)
            gameList.remove(6)
        }
        if (col3[0] == opponentState) {
            opponentList.add(7)
            gameList.remove(7)
        }
        if (col3[1] == opponentState) {
            opponentList.add(8)
            gameList.remove(8)
        }
        if (col3[2] == opponentState) {
            opponentList.add(9)
            gameList.remove(9)
        }
    }

}