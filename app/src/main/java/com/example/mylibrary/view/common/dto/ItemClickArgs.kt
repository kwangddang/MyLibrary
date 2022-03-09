package com.example.mylibrary.view.common.dto

import android.view.View
import androidx.databinding.ViewDataBinding

data class ItemClickArgs(
    val item: ViewDataBinding?,
    val view: View?,
    val position: Int
)
