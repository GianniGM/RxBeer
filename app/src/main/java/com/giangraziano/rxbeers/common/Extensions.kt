package com.giangraziano.rxbeers.common

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by giannig on 11/02/18.
 */

fun RecyclerView.setColumnsLayout(ctx: Context) {
    val displayMetrics = ctx.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    val nColumns = (dpWidth / 180).toInt()

    layoutManager = GridLayoutManager(ctx, nColumns)
}
