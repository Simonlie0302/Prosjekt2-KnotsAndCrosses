package com.ikt205.knotsandcrosses

import android.app.AlertDialog
import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.EditText
import com.ikt205.knotsandcrosses.GameManager.contendersTurn
import com.ikt205.knotsandcrosses.GameManager.pollState
import kotlinx.coroutines.*

class PollService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // TODO(piss)

        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        return super.onStartCommand(intent, flags, startId)
    }
}