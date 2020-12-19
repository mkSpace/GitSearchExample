package com.example.kotlinexample.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): View =
    inflate(LayoutInflater.from(context), layoutId, attachToRoot)

fun ViewGroup?.inflate(
    inflater: LayoutInflater,
    @LayoutRes layoutId: Int,
    attachToRoot: Boolean = false
): View = inflater.inflate(layoutId, this, attachToRoot)