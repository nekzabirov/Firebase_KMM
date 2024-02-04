package com.nekzabirov.firebaseauth.provider

class OAuthProvider(val providerID: String) {
    private val _customParameters = linkedMapOf<String, String>()
    val customParameters: Map<String, String> = _customParameters

    val scopes: List<String> = emptyList()

    fun addCustomParameter(paramKey: String, paramValue: String) {
        _customParameters[paramKey] = paramValue
    }

    fun addCustomParameters(customParameters: Map<String, String>) {
        _customParameters.putAll(customParameters)
    }
}