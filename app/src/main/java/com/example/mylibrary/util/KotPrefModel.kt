package com.example.mylibrary.util

import com.chibatching.kotpref.KotprefModel

object KotPrefModel: KotprefModel() {
    var loginMethod by stringPref()
}