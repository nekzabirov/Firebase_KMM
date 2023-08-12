package com.nekzabirov.firestorage

import cocoapods.FirebaseStorage.FIRStorage

actual class KFireStorage {
    actual companion object {
        private val instance by lazy { KFireStorage() }
        actual fun getInstance(): KFireStorage = instance
    }

    private val storage = FIRStorage.storage()

    actual val reference: KStorageReference
        get() = storage.reference()
}