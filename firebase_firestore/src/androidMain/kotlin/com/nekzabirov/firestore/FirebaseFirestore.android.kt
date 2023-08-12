package com.nekzabirov.firestore

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.ZoneId
import java.util.Date

actual typealias FirebaseFirestore = com.google.firebase.firestore.FirebaseFirestore
actual typealias CollectionReference = com.google.firebase.firestore.CollectionReference
actual typealias DocumentReference = com.google.firebase.firestore.DocumentReference
actual typealias FireTimeStamp = com.google.firebase.Timestamp
actual typealias FireQuery = com.google.firebase.firestore.Query
actual typealias DocumentSnapshot = com.google.firebase.firestore.DocumentSnapshot
actual object FirebaseFirestoreKt {
    @SuppressLint("StaticFieldLeak")
    private var instance: FirebaseFirestore? = null
    actual fun getInstance(): FirebaseFirestore {
        if (instance == null)
            instance = Firebase.firestore
        return instance!!
    }
}

actual fun LocalDate.toFireTimestamp(): FireTimeStamp {
    return FireTimeStamp(
        Date.from(this.toJavaLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
}

actual fun LocalDateTime.toFireTimestamp(): FireTimeStamp {
    return FireTimeStamp(
        Date.from(this.toJavaLocalDateTime().atZone(ZoneId.systemDefault()).toInstant())
    )
}

actual fun FirebaseFirestore.collection(url: String): CollectionReference =
    this.collection(url)

actual fun CollectionReference.document(url: String): DocumentReference =
    this.document(url)

actual suspend fun CollectionReference.addKt(data: Map<String, Any?>): DocumentReference {
    return this.add(data).await()
}

actual fun DocumentReference.collection(url: String): CollectionReference {
    return this.collection(url)
}

actual suspend fun DocumentReference.set(data: Map<String, Any?>) {
    this.set(data).await()
}

actual fun DocumentReference.idKt(): String {
    return this.id
}

actual fun FireQuery.whereGreaterThanOrEqualTo(key: String, value: Any): FireQuery {
    return this.whereGreaterThanOrEqualTo(key, value)
}

actual fun FireQuery.whereLessThanOrEqualTo(key: String, value: Any): FireQuery {
    return this.whereLessThanOrEqualTo(key, value)
}

actual fun FireQuery.whereArrayContains(key: String, value: Any): FireQuery {
    return this.whereArrayContains(key, value)
}

actual fun FireQuery.orderBy(key: String): FireQuery {
    return this.orderBy(key)
}

actual suspend fun FireQuery.documents(): List<DocumentSnapshot> =
    this.get().await().documents

actual fun DocumentSnapshot.idKt(): String =
    this.id

actual fun DocumentSnapshot.getStringKt(key: String): String? =
    this.getString(key)

actual fun DocumentSnapshot.getLocalDateTime(key: String): LocalDateTime? =
    this.getDate(key)?.let {
        it.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }?.toKotlinLocalDateTime()

actual fun DocumentSnapshot.getArrayString(key: String): List<String>? =
    this.get(key) as? List<String>

actual fun DocumentSnapshot.getBool(key: String): Boolean? {
    return this.getBoolean(key)
}

actual fun DocumentSnapshot.reference(): DocumentReference {
    return this.reference
}