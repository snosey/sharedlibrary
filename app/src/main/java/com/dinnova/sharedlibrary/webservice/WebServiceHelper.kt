package com.dinnova.sharedlibrary.webservice

import com.google.gson.annotations.Expose

class WebServiceHelper {


    inner class Pagination : JsonConverter2() {
        @Expose
        var CurrentPage: Int = 0
        @Expose
        var TotalPages: Int = 0
        @Expose
        var PageSize: Int = 0
        @Expose
        var TotalCount: Int = 0
        @Expose
        var HasPrevious: Boolean = false
        @Expose
        var HasNext: Boolean = false
        @Expose
        var PrevoisPageLink: String? = null
        @Expose
        var NextPageLink: String? = null
    }

    inner class Status : JsonConverter2() {
        @Expose
        var Success: Boolean = false
        @Expose
        var ErrorMessage: String? = null
        @Expose
        var ExceptionMessage: String? = null
    }

    inner class CustomResponse : JsonConverter2() {
        @Expose
        var pagination: Pagination? = null
        @Expose
        var status: Status? = null
        @Expose
        var json: String? = null
    }
}