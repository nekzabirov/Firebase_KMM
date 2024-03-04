package com.nekzabirov.firebaseauth.user

data class KUserProfileChangeRequest internal constructor(val displayName: String?, val photoUrl: String?) {
    class Builder {
        var displayName: String? = null
        var photoUrl: String? = null

        internal fun build() =
            KUserProfileChangeRequest(displayName = displayName, photoUrl = photoUrl)
    }
}

fun userProfileChangeRequest(builder: (KUserProfileChangeRequest.Builder) -> Unit): KUserProfileChangeRequest {
    return KUserProfileChangeRequest.Builder().apply(builder).build()
}