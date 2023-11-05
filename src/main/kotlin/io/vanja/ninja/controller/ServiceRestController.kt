package io.vanja.ninja.controller

import io.swagger.v3.oas.annotations.tags.Tag
import io.vanja.ninja.data.ServiceCreateRequest
import io.vanja.ninja.domain.Service
import io.vanja.ninja.service.RmmService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/services")
@Tag(name = "services", description = "The Services Resource")
class ServiceRestController(
    private val rmmService: RmmService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/")
    fun createService(@RequestBody serviceCreateRequest: ServiceCreateRequest): ResponseEntity<Void> {
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(rmmService.createService(serviceCreateRequest).id).toUri()
        ).build()
    }

    @GetMapping("/{id}")
    fun findService(@PathVariable("id") id: Long): ResponseEntity<Service> {
        return ResponseEntity.ok(rmmService.findService(id))
    }

    @DeleteMapping("/{id}")
    fun deleteService(@PathVariable("id") id: Long): ResponseEntity<Void> {
        rmmService.deleteService(id)
        return ResponseEntity.noContent().build()
    }
}