package com.devwindsw.linkrust

import com.sun.jna.Callback;

interface JNACallback : Callback {
    fun invoke(string: String?)
}