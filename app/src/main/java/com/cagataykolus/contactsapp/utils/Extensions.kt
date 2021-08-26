@file:Suppress("unused")

package com.cagataykolus.contactsapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Çağatay Kölüş on 25.08.2021.
 * cagataykolus@gmail.com
 */

fun Any.getCurrentDate(): String {
    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    return df.format(Calendar.getInstance().time)

}