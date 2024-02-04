@file:OptIn(ExperimentalForeignApi::class)
@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.nekzabirov.firebaseapp

import cocoapods.FirebaseCore.FIRApp
import kotlinx.cinterop.ExperimentalForeignApi

class KFirebaseAppImpl internal constructor(private val firebaseApp: FIRApp): KFirebaseApp {
    @OptIn(ExperimentalForeignApi::class)
    override val name: String
        get() = firebaseApp.name

    override val isDefaultApp: Boolean
        get() = TODO("Not yet implemented")

    override val persistenceKey: String
        get() = TODO("Not yet implemented")

    override var isDataCollectionDefaultEnabled: Boolean
        get() = firebaseApp.isDataCollectionDefaultEnabled()
        set(value) {
            firebaseApp.setDataCollectionDefaultEnabled(value)
        }

    @OptIn(ExperimentalForeignApi::class)
    override fun delete() =
        firebaseApp.deleteApp {  }

    override fun setAutomaticResourceManagementEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun addBackgroundStateChangeListener(listener: KFirebaseApp.BackgroundStateChangeListener) {
        TODO("Not yet implemented")
    }

    override fun removeBackgroundStateChangeListener(listener: KFirebaseApp.BackgroundStateChangeListener) {
        TODO("Not yet implemented")
    }
}

internal actual fun kInitializeFirebase(context: KContext): KFirebaseApp? {
    FIRApp.configure()
    FIRApp.initialize()
    return FIRApp.defaultApp()?.let { KFirebaseAppImpl(it) }
}

internal actual fun kGetApps(context: KContext): List<KFirebaseApp> = FIRApp.allApps()
    ?.values
    ?.filterNotNull()
    ?.map { KFirebaseAppImpl(it as FIRApp) }
    ?: emptyList()

internal actual fun kGetInstance(): KFirebaseApp =
    KFirebaseAppImpl(FIRApp.defaultApp()!!)

internal actual fun kGetInstance(name: String): KFirebaseApp =
    KFirebaseAppImpl(FIRApp.appNamed(name)!!)