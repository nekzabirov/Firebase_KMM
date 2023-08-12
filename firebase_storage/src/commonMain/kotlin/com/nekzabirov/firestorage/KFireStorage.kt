package com.nekzabirov.firestorage

expect class KFireStorage {
    companion object {
        fun getInstance(): KFireStorage
    }

    val reference: KStorageReference
}