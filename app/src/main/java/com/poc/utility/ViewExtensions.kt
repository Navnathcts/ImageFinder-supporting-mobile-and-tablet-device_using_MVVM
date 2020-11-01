package com.poc.utility

import android.content.Context
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(context: Context, link: String?, placeHolderRes: Int, errorResId: Int) {
    Picasso.with(context).load(link).fit().centerCrop()
        .placeholder(placeHolderRes)
        .error(errorResId)
        .into(this)
}

@ExperimentalCoroutinesApi
fun AppCompatEditText.textInputAsFlow() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { textInput: CharSequence?, _, _, _ ->
        offer(textInput)
    }
    awaitClose { this@textInputAsFlow.removeTextChangedListener(watcher) }
}

fun View.hideKeyboard(context: Context) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        this.getWindowToken(),
        0
    )
}
