package com.nekzabirov.firebaseauth.user

import android.net.Uri
import com.google.firebase.auth.UserProfileChangeRequest

actual typealias UserUpdateProfileRequest = UserProfileChangeRequest.Builder

actual var UserUpdateProfileRequest.displayName: String?
    get() = this.displayName
    set(value) {
        this.setDisplayName(value)
    }
actual var UserUpdateProfileRequest.photoUrl: String?
    get() = this.photoUri?.toString()
    set(value) {
        if (value == null)
            this.setPhotoUri(null)
        else
            this.setPhotoUri(Uri.parse(value))
    }