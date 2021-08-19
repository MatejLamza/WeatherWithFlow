package com.example.weatherappflow.utils.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.setImageUrl(
    url: String?,
    withRequestManager: (RequestManager.() -> Unit)? = null,
    withRequestBuilder: (RequestBuilder<Drawable>.() -> Unit)? = null
) {
    Glide.with(this)
        .apply { withRequestManager?.invoke(this) }
        .load(url)
        .apply { withRequestBuilder?.invoke(this) }
        .into(this)
}

fun <T : View> T.visibleIf(
    condition: () -> Boolean,
    invisibleStatus: Int = View.GONE,
    apply: T.() -> Unit
) {
    if (condition.invoke()) {
        visibility = View.VISIBLE
        apply.invoke(this)
    } else {
        visibility = invisibleStatus
    }
}

fun <T : View> T.visibleIf(
    condition: Boolean,
    invisibleStatus: Int = View.GONE,
    apply: T.() -> Unit
) = visibleIf({ condition }, invisibleStatus, apply)
