package com.ikt205.knotsandcrosses

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ikt205.knotsandcrosses.Data.Game
import com.ikt205.knotsandcrosses.Data.GameState
import org.json.JSONObject
import java.util.*

typealias GameServiceCallback = (state: Game?, errorCode: Int?) -> Unit

class GameService(context: Context) {

    /// For performing requests in a queue
    private val requestQue: RequestQueue = Volley.newRequestQueue(context)

    fun APIEndpoints(): String {
        val url = Uri.Builder().apply {
            scheme("https")
            authority("generic-game-service.herokuapp.com")
            appendPath("Game")
            build()
        }.toString()

        return url
    }


    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {

        val url = APIEndpoints()

        print(url + "\n")

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state", state)

        val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
            {
                // Success game created.
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                print(game)
                callback(game, null)
            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "gjF9ebA3Ix"
                return headers
            }
        }

        requestQue.add(request)
    }

    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {
        // Build my designated URL
        val urlJoin: String = Uri.Builder().apply {
            scheme("https")
            authority("generic-game-service.herokuapp.com")
            appendPath("Game")
            appendPath(gameId)
            appendPath("join")
            build()
        }.toString()

        print(urlJoin + "\n")

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId", gameId)

        val request = object : JsonObjectRequest(Request.Method.POST, urlJoin, requestData,
            {
                // Success game created.
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                print(game)
                callback(game, null)
            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "gjF9ebA3Ix"
                return headers
            }
        }

        requestQue.add(request)
    }

    fun updateGame(gameId: String, gameState: GameState, callback: GameServiceCallback) {
        // Build my designated URL
        val urlJoin: String = Uri.Builder().apply {
            scheme("https")
            authority("generic-game-service.herokuapp.com")
            appendPath("Game")
            appendPath(gameId)
            appendPath("update")
            build()
        }.toString()

        print(urlJoin + "\n")

        val requestData = JSONObject()
        requestData.put("state", gameState)
        requestData.put("gameId", gameId)

        val request = object : JsonObjectRequest(Request.Method.POST, urlJoin, requestData,
            {
                // Success game created.
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                print(game)
                callback(game, null)
            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "gjF9ebA3Ix"
                return headers
            }
        }

        requestQue.add(request)
    }

    fun pollGame(gameId: String, callback: GameServiceCallback) {
        // Build my designated URL with the gameId
        val urlJoin: String = Uri.Builder().apply {
            scheme("https")
            authority("generic-game-service.herokuapp.com")
            appendPath("Game")
            appendPath(gameId)
            appendPath("poll")
            build()
        }.toString()

        println(urlJoin)

        val requestData = JSONObject()

        requestData.put("gameId", gameId)
        val request = object : JsonObjectRequest(Request.Method.GET, urlJoin, requestData,
            {
                // Success game created.
                val newState = Gson().fromJson(it.toString(0), Game::class.java)
                callback(newState, null)
                println(newState)
            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            }) {
            // Setting a header to the designated content type and game service key from swagger description
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "gjF9ebA3Ix"
                return headers
            }
        }

        requestQue.add(request)
    }

}