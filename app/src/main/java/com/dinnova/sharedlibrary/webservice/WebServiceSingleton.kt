package com.dinnova.sharedlibrary.webservice

object WebServiceSingleton {
    private lateinit var baseURL: String
    fun getBaseURL(): String {
        return baseURL
    }

    fun setBaseURL(baseURL: String) {
        WebServiceSingleton.baseURL = baseURL
    }
}