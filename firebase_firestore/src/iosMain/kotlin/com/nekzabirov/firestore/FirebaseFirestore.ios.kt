package com.nekzabirov.firestore

import cocoapods.FirebaseFirestore.FIRCollectionReference
import cocoapods.FirebaseFirestore.FIRDocumentReference
import cocoapods.FirebaseFirestore.FIRDocumentSnapshot
import cocoapods.FirebaseFirestore.FIRFirestore
import cocoapods.FirebaseFirestore.FIRQuery
import cocoapods.FirebaseFirestore.FIRTimestamp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSDateComponents
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual typealias FirebaseFirestore = FIRFirestore
actual typealias CollectionReference = FIRCollectionReference
actual typealias DocumentReference = FIRDocumentReference
actual typealias FireTimeStamp = FIRTimestamp
actual typealias FireQuery = FIRQuery
actual typealias DocumentSnapshot = FIRDocumentSnapshot

actual object FirebaseFirestoreKt {
    actual fun getInstance(): FirebaseFirestore {
        return FIRFirestore.firestore()
    }
}

actual fun LocalDate.toFireTimestamp(): FireTimeStamp {
    return FIRTimestamp.timestampWithDate(this.toNSDateComponents().date!!)
}

actual fun LocalDateTime.toFireTimestamp(): FireTimeStamp {
    return FIRTimestamp.timestampWithDate(this.toNSDateComponents().date!!)
}

actual fun FirebaseFirestore.collection(url: String): CollectionReference =
    this.collectionWithPath(url)

actual fun CollectionReference.document(url: String): DocumentReference =
    this.documentWithPath(url)

actual suspend fun CollectionReference.addKt(data: Map<String, Any?>): DocumentReference {
    val newData = hashMapOf<Any?, Any?>()

    data.forEach {
        newData[it.key] = it.value
    }

    return this.addDocumentWithData(newData)
}

actual fun DocumentReference.collection(url: String): CollectionReference =
    this.collectionWithPath(url)

actual suspend fun DocumentReference.set(data: Map<String, Any?>) {
    val newData = hashMapOf<Any?, Any?>()

    data.forEach {
        newData[it.key] = it.value
    }

    this.setData(newData)
}

actual fun DocumentReference.idKt(): String {
    return this.documentID
}

actual fun FireQuery.whereGreaterThanOrEqualTo(key: String, value: Any): FireQuery {
    return this.queryWhereField(key, isGreaterThanOrEqualTo = value)
}

actual fun FireQuery.whereLessThanOrEqualTo(key: String, value: Any): FireQuery {
    return this.queryWhereField(key, isLessThanOrEqualTo = value)
}

actual fun FireQuery.whereArrayContains(key: String, value: Any): FireQuery {
    return this.queryWhereField(key, arrayContains = value)
}

actual fun FireQuery.orderBy(key: String): FireQuery {
    return this.queryOrderedByField(key)
}

actual suspend fun FireQuery.documents(): List<DocumentSnapshot> = suspendCoroutine { cont ->
    this.getDocumentsWithCompletion { firQuerySnapshot, nsError ->
        if (nsError != null) {
            cont.resumeWithException(Exception(nsError.localizedDescription))
            return@getDocumentsWithCompletion
        } else {
            (firQuerySnapshot!!.documents() as List<DocumentSnapshot>).also {
                cont.resume(it)
            }
        }
    }
}

actual fun DocumentSnapshot.idKt(): String = this.documentID

actual fun DocumentSnapshot.getStringKt(key: String): String? =
    this.data()?.get(key) as? String

actual fun DocumentSnapshot.getLocalDateTime(key: String): LocalDateTime? =
    (this.data()?.get(key) as? FIRTimestamp)?.dateValue()?.toKotlinInstant()
        ?.toLocalDateTime(TimeZone.currentSystemDefault())

actual fun DocumentSnapshot.getArrayString(key: String): List<String>? =
    (this.data()?.get(key) as? Array<String>)?.toList()

actual fun DocumentSnapshot.getBool(key: String): Boolean? {
    return this.data()?.get(key) as? Boolean
}

actual fun DocumentSnapshot.reference(): DocumentReference {
    return this.reference
}