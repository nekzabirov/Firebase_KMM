package com.nekzabirov.firebaseapp

import android.annotation.SuppressLint
import com.google.firebase.FirebaseApp

class KFirebaseAppImpl internal constructor(private val firebaseApp: FirebaseApp) : KFirebaseApp {
    private val backgroundStateChangeListeners = arrayListOf<KFirebaseApp.BackgroundStateChangeListener>()

    init {
        firebaseApp.addBackgroundStateChangeListener { value ->
            backgroundStateChangeListeners.forEach {
                it.onBackgroundStateChanged(value)
            }
        }
    }

    override val name: String
        get() = firebaseApp.name

    override val isDefaultApp: Boolean
        @SuppressLint("VisibleForTests")
        get() = firebaseApp.isDefaultApp

    override val persistenceKey: String
        get() = firebaseApp.persistenceKey

    override var isDataCollectionDefaultEnabled: Boolean
        get() = firebaseApp.isDataCollectionDefaultEnabled
        set(value) {
            firebaseApp.setDataCollectionDefaultEnabled(value)
        }

    override fun delete() =
        firebaseApp.delete()

    override fun setAutomaticResourceManagementEnabled(enabled: Boolean) =
        firebaseApp.setAutomaticResourceManagementEnabled(enabled)

    override fun addBackgroundStateChangeListener(listener: KFirebaseApp.BackgroundStateChangeListener) {
        backgroundStateChangeListeners.add(listener)
    }

    override fun removeBackgroundStateChangeListener(listener: KFirebaseApp.BackgroundStateChangeListener) {
        backgroundStateChangeListeners.remove(listener)
    }
}

internal actual fun kInitializeFirebase(context: KContext): KFirebaseApp? =
    FirebaseApp.initializeApp(context)?.let { KFirebaseAppImpl(it) }

internal actual fun kGetApps(context: KContext): List<KFirebaseApp> =
    FirebaseApp.getApps(context).map { KFirebaseAppImpl(it) }

internal actual fun kGetInstance(): KFirebaseApp =
    KFirebaseAppImpl(FirebaseApp.getInstance())

internal actual fun kGetInstance(name: String): KFirebaseApp =
    KFirebaseAppImpl(FirebaseApp.getInstance(name))