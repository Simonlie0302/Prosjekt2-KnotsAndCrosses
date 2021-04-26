package com.ikt205.knotsandcrosses

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ikt205.knotsandcrosses.R
import com.ikt205.knotsandcrosses.Game
import com.ikt205.knotsandcrosses.GameState
import com.ikt205.knotsandcrosses.App
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.schedule

typealias GameServiceCallback = (state: Game?, errorCode: Int?) -> Unit

/*  NOTE:
    Using object expression to make GameService a Singleton.
    Why? Because there should only be one active GameService ever.
 */

class GameService(context: Context) {

    /// NOTE: Do not want to have App.context all over the code. Also it is nice if we later want to support different contexts
    //private val context = App.context

    /// NOTE: God practice to use a que for performing requests.
    private val requestQue: RequestQueue = Volley.newRequestQueue(context)

    /// NOTE: One posible way of constructing a list of API url. You want to construct the urls so that you can support different environments (i.e. Debug, Test, Prod etc)
    fun APIEndpoints(): String {

        val url = Uri.Builder().apply {
            scheme("https")
            authority("generic-game-service.herokuapp.com")
            appendPath("Game")
//            appendPath("types")
//            appendQueryParameter("type", "1")
//            appendQueryParameter("sort", "relevance")
//            fragment("section-name")
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

        val url = APIEndpoints()

        val urlJoin: String = Uri.Builder().apply {
            // url
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
        val url = APIEndpoints()

        val urlJoin: String = Uri.Builder().apply {
            // url
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
        val urlJoin: String = Uri.Builder().apply {
            // url
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