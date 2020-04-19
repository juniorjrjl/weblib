package com.library.weblib.api.exceptionhandler

import java.time.OffsetDateTime




class Problem private constructor(
        var status: Int = 0,

        var timestamp: OffsetDateTime = OffsetDateTime.now(),

        var type: String? = null,

        var title: String? = null,

        var detail: String? = null,

        var userMessage: String? = null,

        var fields: List<Any>? = null
){
    data class Builder(
            var status: Int,
            var timestamp: OffsetDateTime
    ){

        var type: String = ""

        var title: String = ""

        var detail: String = ""

        var userMessage: String = ""

        var fields: List<Object>? = emptyList()

        fun problem(problemType: ProblemType) = apply{
            this.type = problemType.uri
            this.title = problemType.title
        }
        fun detail(detail: String) = apply { this.detail = detail }
        fun userMessage(userMessage: String) = apply { this.userMessage = userMessage }
        fun fields(fields: List<Object>?) = apply { this.fields = fields }
        fun build() = Problem(status, timestamp, type, title, detail, userMessage, fields)

    }

}

class Object {
    var name: String = ""

    var userMessage: String = ""
}