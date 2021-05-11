package com.ikt205.knotsandcrosses

import com.ikt205.knotsandcrosses.GameManager.winMessage
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun check_gameWin(){
        assertEquals(winMessage == "I won!",
            Game(mutableListOf("test1", "test2"), "gameId", "[[0, 1, 0], [0, 1, 0], [0, 1, 0]]" ))
    }

    @Test
    fun check_gameLose(){
        assertEquals(winMessage == "Opponent win!",
            Game(mutableListOf("test1", "test2"), "gameId", "[[0, 0, 2], [0, 0, 2], [0, 0, 2]]" ))
    }

    @Test
    fun check_numberOfPlayers(){
        assertEquals(2,
            Game(mutableListOf("test1", "test2"), "gameId", "[[0, 0, 0], [0, 0, 0], [0, 0, 0]]").numbersOfPlayers())
    }

    @Test
    fun check_tilesTaken(){
        assertEquals(6,
            Game(mutableListOf("test1", "test2"), "gameId", "[[0, 1, 0], [0, 2, 0], [1, 0, 0]]").tilesTaken())
    }
}