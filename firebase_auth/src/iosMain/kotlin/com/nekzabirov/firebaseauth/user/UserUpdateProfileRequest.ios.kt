package com.nekzabirov.firebaseauth.user

import cocoapods.FirebaseAuth.FIRUserProfileChangeRequest
import platform.Foundation.NSURL.Companion.URLWithString

actual typealias UserUpdateProfileRequest = FIRUserProfileChangeRequest

actual var UserUpdateProfileRequest.displayName: String?
    get() = this.displayName()
    set(value) {
        this.setDisplayName(value)
    }
actual var UserUpdateProfileRequest.photoUrl: String?
    get() = this.photoURL()?.absoluteString
    set(value) {
        this.setPhotoURL(value?.let { URLWithString(it) })
    }