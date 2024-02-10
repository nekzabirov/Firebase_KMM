package com.nekzabirov.firebaseapp

/**
 * Represents a Firebase App instance.
 */
interface KFirebaseApp {
    /** The name of the Firebase App. */
    val name: String

    /** Indicates whether this is the default Firebase App. */
    val isDefaultApp: Boolean

    /** The key used for persistence. */
    val persistenceKey: String

    /** Indicates whether data collection is enabled by default. */
    var isDataCollectionDefaultEnabled: Boolean

    /** Deletes this Firebase App instance. */
    fun delete()

    /**
     * Sets whether automatic resource management is enabled.
     * @param enabled True to enable automatic resource management, false otherwise.
     */
    fun setAutomaticResourceManagementEnabled(enabled: Boolean)

    /**
     * Adds a listener to track background state changes.
     * @param listener The listener to add.
     */
    fun addBackgroundStateChangeListener(listener: BackgroundStateChangeListener)

    /**
     * Removes a listener for background state changes.
     * @param listener The listener to remove.
     */
    fun removeBackgroundStateChangeListener(listener: BackgroundStateChangeListener)

    /** Listener interface for background state changes. */
    interface BackgroundStateChangeListener {
        /**
         * Called when the background state changes.
         * @param isBackgrounded True if the app is in the background, false otherwise.
         */
        fun onBackgroundStateChanged(isBackgrounded: Boolean)
    }

    /**
     * Companion object containing functions for obtaining Firebase App instances.
     */
    companion object {
        /**
         * Retrieves a list of Firebase App instances.
         * @param context The context used to retrieve the apps.
         * @return List of Firebase App instances.
         */
        fun getApps(context: KContext): List<KFirebaseApp> =
            kGetApps(context)

        /**
         * Retrieves the default Firebase App instance.
         * @return Default Firebase App instance.
         */
        fun getInstance(): KFirebaseApp =
            kGetInstance()

        /**
         * Retrieves a Firebase App instance by name.
         * @param name The name of the Firebase App to retrieve.
         * @return Firebase App instance with the specified name.
         */
        fun getInstance(name: String): KFirebaseApp =
            kGetInstance(name)

        /**
         * Initializes a Firebase App instance.
         * @param context The context to initialize the Firebase App with.
         * @return Initialized Firebase App instance, or null if initialization fails.
         */
        fun initializeApp(context: KContext): KFirebaseApp? =
            kInitializeFirebase(context)
    }
}

/**
 * Initializes a Firebase App instance.
 * @param context The context to initialize the Firebase App with.
 * @return Initialized Firebase App instance, or null if initialization fails.
 */
internal expect fun kInitializeFirebase(context: KContext): KFirebaseApp?

/**
 * Retrieves a list of Firebase App instances.
 * @param context The context used to retrieve the apps. AndroidApplication or USStandard
 * @return List of Firebase App instances.
 */
internal expect fun kGetApps(context: KContext): List<KFirebaseApp>

/**
 * Retrieves the default Firebase App instance.
 * @return Default Firebase App instance.
 */
internal expect fun kGetInstance(): KFirebaseApp

/**
 * Retrieves a Firebase App instance by name.
 * @param name The name of the Firebase App to retrieve.
 * @return Firebase App instance with the specified name.
 */
internal expect fun kGetInstance(name: String): KFirebaseApp

