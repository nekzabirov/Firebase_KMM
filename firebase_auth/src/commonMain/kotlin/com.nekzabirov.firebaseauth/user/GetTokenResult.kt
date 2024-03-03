package com.nekzabirov.firebaseauth.user

data class GetTokenResult(
    val token: String?,

    val claims: MutableMap<String?, Any?>,

    val authTimestamp: Long,

    val signInProvider: String?,

    val issuedAtTimestamp: Long,

    val expirationTimestamp: Long,

    val signInSecondFactor: String?
)
