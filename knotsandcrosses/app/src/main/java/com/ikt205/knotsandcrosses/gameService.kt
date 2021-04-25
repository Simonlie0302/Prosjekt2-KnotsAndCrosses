package com.ikt205.knotsandcrosses

import android.content.Context
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
    private enum class APIEndpoints(val url: String) {
        CREATE_GAME(
            "%1s%2s%3s".format(
                (R.string.protocol),
                (R.string.domain),
                (R.string.base_path)
            )
        )
    }


    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {

        // val url = APIEndpoints.CREATE_GAME.url
        val url = "https://generic-game-service.herokuapp.com/Game"

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

    }

    fun updateGame(gameId: String, gameState: GameState, callback: GameServiceCallback) {

    }

    fun pollGame(gameId: String, callback: GameServiceCallback) {

    }

}