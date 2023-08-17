package com.nekzabirov.firefunction

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

actual class KFireFunction actual constructor() {
    actual companion object {
        actual val instance: KFireFunction by lazy { KFireFunction() }
    }

    private val functions = Firebase.functions
    actual suspend fun call(route: String, payload: Map<String, Any>): Any? = functions
        .getHttpsCallable(route)
        .call(payload)
        .await()
        .data

}