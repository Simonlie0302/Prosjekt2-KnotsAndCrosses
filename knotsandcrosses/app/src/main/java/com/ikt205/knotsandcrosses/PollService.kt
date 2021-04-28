package com.ikt205.knotsandcrosses

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.widget.Button
import com.ikt205.knotsandcrosses.GameManager.pollState
import com.ikt205.knotsandcrosses.GameManager.contendersTurn
import kotlinx.coroutines.*

class PollService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // TODO(piss)

        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        GlobalScope.launch {

            while (true) {
                // launch a new coroutine in background and continue

                while (contendersTurn) {
                    delay(5000)
                    var tempList = pollState

                    GameManager.pollGame(applicationContext)
                    delay(1000)

                    if (!(tempList.containsAll(pollState) && pollState.containsAll(tempList))) {
                        println("Min tur")
                        contendersTurn = false

                    } else {
                        //GameActivity().turnBased()
                        println("Motstanders trekk")
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}