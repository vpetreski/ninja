package io.vanja.ninja.data

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.io.Serializable

data class DeviceCreateRequest(
    @field:NotBlank val name: String,
    @field:NotBlank val type: String
)

data class ServiceDeviceRequest(
    @field:Positive val serviceId: Long
)

data class Cost(
    val cost: Long
) : Serializable

data class ServiceCreateRequest(
    @field:NotBlank val type: String,
    @field:Positive val price: Long
)