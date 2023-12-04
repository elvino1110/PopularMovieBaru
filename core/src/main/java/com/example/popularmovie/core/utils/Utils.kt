package com.example.popularmovie.core.utils

import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat

import java.util.*

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val result = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
    val date = format.parse(this) as Date

    return result.format(date)
}
