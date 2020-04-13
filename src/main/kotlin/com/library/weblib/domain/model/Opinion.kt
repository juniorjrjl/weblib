package com.library.weblib.domain.model

import java.time.ZonedDateTime
import javax.persistence.*


/**
* POKO to representate Opinion
*/
data class Opinion(
        var id: Long,
        var content: String
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