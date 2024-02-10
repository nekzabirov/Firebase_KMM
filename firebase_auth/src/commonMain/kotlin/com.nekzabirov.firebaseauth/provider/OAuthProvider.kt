package com.nekzabirov.firebaseauth.provider

/**
 * Class representing an OAuth provider for custom authentication.
 * This can be used for custom OAuth providers in FirebaseAuth SDK.
 *
 * @param providerID The identifier for the custom OAuth provider.
 *
 * use in [com.nekzabirov.firebaseauth.KFirebaseAuth.signInWithProvider]
 */
class OAuthProvider(val providerID: String) {
    private val _customParameters = linkedMapOf<String, String>()

    /**
     * Read-only view of the custom parameters map.
     */
    val customParameters: Map<String, String> = _customParameters

    private val _scopes = arrayListOf<String>()

    /**
     * List of scopes associated with the OAuth provider.
     */
    val scopes: List<String> = _scopes

    /**
     * Adds a custom parameter to the OAuth provider.
     *
     * @param paramKey The key of the custom parameter.
     * @param paramValue The value of the custom parameter.
     */
    fun addCustomParameter(paramKey: String, paramValue: String) {
        _customParameters[paramKey] = paramValue
    }

    /**
     * Adds multiple custom parameters to the OAuth provider.
     *
     * @param customParameters A map containing custom parameters to be added.
     */
    fun addCustomParameters(customParameters: Map<String, String>) {
        _customParameters.putAll(customParameters)
    }

    fun addScope(scope: String) {
        _scopes.add(scope)
    }
}