package com.example.mylibrary.view.common

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.example.mylibrary.R

class MorePopupMenu private constructor(context: Context, view: View): PopupMenu(context, view) {

    constructor(context: Context, view: View, listener: OnMenuItemClickListener): this(context, view) {
        menuInflater.inflate(R.menu.popupmenu_more, menu)
        setOnMenuItemClickListener(listener)
    }
}