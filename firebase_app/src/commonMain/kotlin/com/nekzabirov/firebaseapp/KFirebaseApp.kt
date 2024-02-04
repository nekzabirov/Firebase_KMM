package com.nekzabirov.firebaseapp

interface KFirebaseApp {
    val name: String

    val isDefaultApp: Boolean

    val persistenceKey: String

    var isDataCollectionDefaultEnabled: Boolean

    fun delete()

    fun setAutomaticResourceManagementEnabled(enabled: Boolean)

    fun addBackgroundStateChangeListener(listener: BackgroundStateChangeListener)

    fun removeBackgroundStateChangeListener(listener: BackgroundStateChangeListener)

    companion object {
        fun getApps(context: KContext): List<KFirebaseApp> =
            kGetApps(context)

        fun getInstance(): KFirebaseApp =
            kGetInstance()

        fun getInstance(name: String): KFirebaseApp =
            kGetInstance(name)

        fun initializeApp(context: KContext): KFirebaseApp? =
            kInitializeFirebase(context)
    }

    interface BackgroundStateChangeListener {
        fun onBackgroundStateChanged(var1: Boolean)
    }
}

internal expect fun kInitializeFirebase(context: KContext): KFirebaseApp?

internal expect fun kGetApps(context: KContext): List<KFirebaseApp>

internal expect fun kGetInstance(): KFirebaseApp

internal expect fun kGetInstance(name: String): KFirebaseApp
