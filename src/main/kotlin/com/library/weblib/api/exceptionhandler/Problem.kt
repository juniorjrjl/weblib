package com.library.weblib.api.exceptionhandler

import java.time.OffsetDateTime

/**
 *
 * POKO class to represent a response with some HttpStatus error
 *
 */
class Problem private constructor(
        var status: Int = 0,

        var timestamp: OffsetDateTime = OffsetDateTime.now(),

        var type: String? = null,

        var title: String? = null,

        var detail: String? = null,

        var userMessage: String? = null,

        var fields: List<Any>? = null
){

    /**
     *
     * class to apply builder patter in <code>{@link Problem}</code> class
     *
     */
    data class Builder(
            var status: Int,
            var timestamp: OffsetDateTime
    ){

        var type: String = ""
            private set

        var title: String = ""
            private set

        var detail: String = ""
            private set

        var userMessage: String = ""
            private set

        var fields: List<Object>? = emptyList()
            private set

        fun problem(problemType: ProblemType) = apply{
            this.type = problemType.uri
            this.title = problemType.title
        }

        fun detail(detail: String) = apply { this.detail = detail }
        fun userMessage(userMessage: String) = apply { this.userMessage = userMessage }
        fun fields(fields: MutableList<Object>?) = apply { this.fields = fields }
        fun build(): Problem = Problem(status, timestamp, type, title, detail, userMessage, fields)

    }

}