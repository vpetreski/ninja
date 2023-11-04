package io.vanja.ninja.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
open class Service(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ninja_service_seq")
    @SequenceGenerator(name = "ninja_service_seq", sequenceName = "ninja_service_seq")
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    open var type: String? = null,

    @NotNull
    @Column(name = "price", nullable = false)
    open var price: Long? = null
)