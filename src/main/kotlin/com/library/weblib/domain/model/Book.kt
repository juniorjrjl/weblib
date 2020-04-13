package com.library.weblib.domain.model

import java.time.ZonedDateTime
import javax.persistence.*


/**
* POKO to representate Book
*/
data class Book(
        var id: Long,
        var name: String,
        var year: Int
){

    var createdAt= ZonedDateTime.now()
    var updatedAt = ZonedDateTime.now()

    @PrePersist
    fun prePersist(){
        createdAt = ZonedDateTime.now()
        updatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate(){
        updatedAt = ZonedDateTime.now()
    }

}