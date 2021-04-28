package com.ikt205.knotsandcrosses

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ikt205.knotsandcrosses.GameManager.pollState
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
                delay(5000)
                val tempList = pollState
                GameManager.pollGame(applicationContext)
                delay(1000)

                if (tempList.toString() != pollState.toString()){
                    print("to string fungerer")
                }

                if(!(tempList.containsAll(pollState) && pollState.containsAll(tempList))){
                    println("listen er ulik")
                }
//                println("new pollstate:"+ pollState)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}