package com.dinnova.sharedlibrary.webservice

object WebServiceSingleton {

    private lateinit var indicator: Indicator
    fun getIndicator(): Indicator {
        return indicator
    }

    fun setIndicator(indicator: Indicator) {
        WebServiceSingleton.indicator = indicator
    }


    private lateinit var baseURL: String
    fun getBaseURL(): String {
        return baseURL
    }

    fun setBaseURL(baseURL: String) {
        WebServiceSingleton.baseURL = baseURL
    }

}