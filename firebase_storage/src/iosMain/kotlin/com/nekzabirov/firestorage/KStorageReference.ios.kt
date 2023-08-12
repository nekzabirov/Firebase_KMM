package com.nekzabirov.firestorage

import cocoapods.FirebaseStorage.FIRStorageReference
import kotlinx.cinterop.*
import platform.Foundation.NSData
import platform.Foundation.create
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual typealias KStorageReference = FIRStorageReference

actual val KStorageReference.path: String
    get() = this.fullPath()

actual val KStorageReference.name: String
    get() = this.name()

actual val KStorageReference.bucket: String
    get() = this.bucket()

actual fun KStorageReference.child(path: String): KStorageReference = this.child(path)

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual suspend fun KStorageReference.putBytes(bytes: ByteArray) = suspendCoroutine { cont ->
    val data = bytes.usePinned {
        NSData.create(bytes = it.addressOf(0), length = bytes.size.convert())
    }

    this.putData(data, null) { _, error ->
        if (error != null) {
            cont.resumeWithException(Exception(error.localizedDescription))
            return@putData
        }

        cont.resume(Unit)
    }
}

actual suspend fun KStorageReference.downloadUrl(): String = suspendCoroutine { cont ->
    this.downloadURLWithCompletion { url, nsError ->
        if (nsError != null)
            cont.resumeWithException(Exception(nsError.localizedDescription()))
        else if (url != null)
            cont.resume(url.absoluteString!!)
        else
            cont.resumeWithException(Exception("Cannot download"))
    }
}

actual suspend fun KStorageReference.delete() = suspendCoroutine { cont ->
    this.deleteWithCompletion { error ->
        if (error != null)
            cont.resumeWithException(Exception(error.localizedDescription()))
        else
            cont.resume(Unit)
    }
}

actual suspend fun KStorageReference.list(): List<KStorageReference> = suspendCoroutine { cont ->
    this.listAllWithCompletion { firStorageListResult, nsError ->
        if (nsError != null)
            cont.resumeWithException(Exception(nsError.localizedDescription))
        else if (firStorageListResult != null)
            cont.resume(firStorageListResult.items().map { it as FIRStorageReference })
        else
            cont.resumeWithException(Exception("Cannot load"))
    }
}