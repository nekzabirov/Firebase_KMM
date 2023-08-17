package com.nekzabirov.firefunction

import cocoapods.FirebaseFunctions.FIRFunctions
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class KFireFunction actual constructor() {
    actual companion object {
        actual val instance: KFireFunction by lazy { KFireFunction() }
    }

    private val functions = FIRFunctions.functions()

    actual suspend fun call(route: String, payload: Map<String, Any>): Any? = suspendCoroutine { cont ->
        functions.HTTPSCallableWithName(route)
            .callWithObject(data = payload) { result, error ->
                if (error != null)
                    cont.resumeWithException(Exception(error.localizedDescription))
                else if (result != null)
                    cont.resume(result.data())
                else
                    cont.resumeWithException(Exception("Unknow"))
            }
    }
}