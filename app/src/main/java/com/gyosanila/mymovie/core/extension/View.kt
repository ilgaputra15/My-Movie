package com.gyosanila.mymovie.core.extension

import android.view.View

/**
 * Created by ilgaputra15
 * on Tuesday, 02/07/2019 10:29
 * Division Mobile - PT.Homecareindo Global Medika
 **/

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if(value) View.VISIBLE else View.GONE
    }
