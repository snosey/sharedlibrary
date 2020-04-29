package com.dinnova.sharedlibrary.webservice

interface Indicator {
    fun showIndicator()
    fun hideIndicator()
    fun isVisible(): Boolean
}