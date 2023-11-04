package io.vanja.ninja.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
open class Device(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ninja_device_seq")
    @SequenceGenerator(name = "ninja_device_seq", sequenceName = "ninja_device_seq")
    @Column(name = "id", nullable = false)
    open var id: Long? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    open var name: String? = null,

    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    open var type: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "device_service",
        joinColumns = [JoinColumn(name = "device_id")],
        inverseJoinColumns = [JoinColumn(name = "service_id")]
    )
    open var services: MutableSet<Service> = mutableSetOf(),
)