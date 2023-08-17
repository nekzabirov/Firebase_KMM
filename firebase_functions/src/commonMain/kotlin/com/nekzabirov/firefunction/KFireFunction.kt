package com.nekzabirov.firefunction

expect class KFireFunction private constructor() {
    companion object {
        val instance: KFireFunction
    }
    suspend fun call(route: String, payload: Map<String, Any>): Any?
}