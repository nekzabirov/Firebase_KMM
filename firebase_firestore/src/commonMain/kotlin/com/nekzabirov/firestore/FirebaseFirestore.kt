package com.nekzabirov.firestore

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

expect class FirebaseFirestore
expect class CollectionReference: FireQuery
expect class DocumentReference
expect class FireTimeStamp
expect open class FireQuery
expect class DocumentSnapshot
expect object FirebaseFirestoreKt {
    fun getInstance(): FirebaseFirestore
}

expect fun LocalDate.toFireTimestamp(): FireTimeStamp

expect fun LocalDateTime.toFireTimestamp(): FireTimeStamp

expect fun FirebaseFirestore.collection(url: String): CollectionReference

expect fun CollectionReference.document(url: String): DocumentReference

expect suspend fun CollectionReference.addKt(data: Map<String, Any?>): DocumentReference

expect fun DocumentReference.collection(url: String): CollectionReference

expect suspend fun DocumentReference.set(data: Map<String, Any?>)

expect fun DocumentReference.idKt(): String

expect fun FireQuery.whereGreaterThanOrEqualTo(key: String, value: Any): FireQuery

expect fun FireQuery.whereLessThanOrEqualTo(key: String, value: Any): FireQuery

expect fun FireQuery.whereArrayContains(key: String, value: Any): FireQuery

expect fun FireQuery.orderBy(key: String): FireQuery

expect suspend fun FireQuery.documents(): List<DocumentSnapshot>

expect fun DocumentSnapshot.idKt(): String

expect fun DocumentSnapshot.getStringKt(key: String): String?

expect fun DocumentSnapshot.getLocalDateTime(key: String): LocalDateTime?

expect fun DocumentSnapshot.getArrayString(key: String): List<String>?

expect fun DocumentSnapshot.getBool(key: String): Boolean?

expect fun DocumentSnapshot.reference(): DocumentReference
