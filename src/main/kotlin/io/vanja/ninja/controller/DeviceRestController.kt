package io.vanja.ninja.controller

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
class DeviceRestController(
    private val rmmService: RmmService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/")
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
        rmmService.addServiceToDevice(id, serviceDeviceRequest)
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