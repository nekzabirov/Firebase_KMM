package com.nekzabirov.firestorage

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

actual class KFireStorage {
    actual companion object {
        private val instance by lazy { KFireStorage() }
        actual fun getInstance(): KFireStorage = instance
    }

    private val storage = Firebase.storage

    actual val reference: KStorageReference
        get() = storage.reference



}