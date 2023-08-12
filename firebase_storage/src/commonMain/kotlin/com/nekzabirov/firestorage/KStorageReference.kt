package com.nekzabirov.firestorage

expect class KStorageReference

expect val KStorageReference.path: String

expect val KStorageReference.name: String

expect val KStorageReference.bucket: String

expect fun KStorageReference.child(path: String): KStorageReference

expect suspend fun KStorageReference.putBytes(bytes: ByteArray)

expect suspend fun KStorageReference.downloadUrl(): String

expect suspend fun KStorageReference.delete()

expect suspend fun KStorageReference.list(): List<KStorageReference>