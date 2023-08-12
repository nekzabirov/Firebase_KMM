package com.nekzabirov.firestorage

import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

actual typealias KStorageReference = StorageReference

actual fun KStorageReference.child(path: String): KStorageReference {
    return this.child(path)
}

actual val KStorageReference.path: String
    get() = this.path

actual val KStorageReference.name: String
    get() = this.name

actual val KStorageReference.bucket: String
    get() = this.bucket

actual suspend fun KStorageReference.putBytes(bytes: ByteArray) {
    this.putBytes(bytes).await()
}

actual suspend fun KStorageReference.downloadUrl(): String = this.downloadUrl.await().toString()

actual suspend fun KStorageReference.delete() = this.delete().await().let { }
actual suspend fun KStorageReference.list(): List<KStorageReference> = this.listAll().await().items