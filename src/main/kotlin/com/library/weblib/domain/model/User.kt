package com.library.weblib.domain.model

import com.github.pozo.KotlinBuilder
import java.time.ZonedDateTime
import javax.persistence.*


/**
 *
 * POKO to representate User
 *
 */
@Entity
@Table(name = "users")
@KotlinBuilder
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
        @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
        @Column(insertable = false, updatable = false, nullable = false ,columnDefinition = "decimal")
        var id: Long,

        @Column(length = 150, nullable = false)
        var name: String,

        @Column(length = 200, nullable = false)
        var email: String,

        @Column(length = 11, nullable = false, columnDefinition = "char(11)")
        var cpf: String
){

    @Column(nullable = false)
    var createdAt: ZonedDateTime = ZonedDateTime.now()
    var updatedAt: ZonedDateTime = ZonedDateTime.now()

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