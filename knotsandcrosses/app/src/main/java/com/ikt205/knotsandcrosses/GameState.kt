package com.ikt205.knotsandcrosses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = List<List<Int>>

@Parcelize
data class Game(val players:MutableList<String>, val gameId:String, val state: String):Parcelable

