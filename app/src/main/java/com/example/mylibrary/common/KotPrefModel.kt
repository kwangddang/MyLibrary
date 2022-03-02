package com.example.mylibrary.common

import com.chibatching.kotpref.KotprefModel

object KotPrefModel: KotprefModel() {
    var loginMethod by stringPref()
}