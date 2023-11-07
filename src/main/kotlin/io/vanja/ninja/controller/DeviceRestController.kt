package io.vanja.ninja.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.vanja.ninja.data.Cost
import io.vanja.ninja.data.DeviceCreateRequest
import io.vanja.ninja.data.ServiceDeviceRequest
import io.vanja.ninja.domain.Device
import io.vanja.ninja.service.RmmService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/devices")
@Tag(name = "devices", description = "The Devices Resource")
class DeviceRestController(
    private val rmmService: RmmService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/")
    @Operation(summary = "Create device")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Device created",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Void::class))],
                headers = [Header(name = "Location", description = "Created device endpoint")]
            ),
            ApiResponse(responseCode = "409", description = "Device already exist")
        ]
    )
    fun createDevice(@RequestBody deviceCreateRequest: DeviceCreateRequest): ResponseEntity<Void> {
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(rmmService.createDevice(deviceCreateRequest).id).toUri()
        ).build()
    }

    @GetMapping("/{id}")
    fun findDevice(@PathVariable("id") id: Long): ResponseEntity<Device> {
        return ResponseEntity.ok(rmmService.findDevice(id))
    }

    @DeleteMapping("/{id}")
    fun deleteDevice(@PathVariable("id") id: Long): ResponseEntity<Void> {
        rmmService.deleteDevice(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/services")
    fun addServiceToDevice(
        @PathVariable("id") id: Long,
        @RequestBody serviceDeviceRequest: ServiceDeviceRequest
    ): ResponseEntity<Void> {
        rmmService.addServiceToDevice(id, serviceDeviceRequest.serviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/services")
    fun removeServiceFromDevice(
        @PathVariable("id") id: Long,
        @RequestBody serviceDeviceRequest: ServiceDeviceRequest
    ): ResponseEntity<Void> {
        rmmService.removeServiceFromDevice(id, serviceDeviceRequest)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}/cost")
    fun deviceCost(@PathVariable("id") id: Long): ResponseEntity<Cost> {
        return ResponseEntity.ok(rmmService.calculateDeviceCost(id))
    }

    @GetMapping("/cost")
    fun totalCost(): ResponseEntity<Cost> {
        return ResponseEntity.ok(rmmService.calculateTotalCost())
    }
}