package io.vanja.ninja.service

import io.vanja.ninja.data.Cost
import io.vanja.ninja.data.DeviceCreateRequest
import io.vanja.ninja.data.ServiceCreateRequest
import io.vanja.ninja.domain.Device
import io.vanja.ninja.exception.AlreadyExistsException
import io.vanja.ninja.exception.DoesNotExistException
import io.vanja.ninja.repository.DeviceRepository
import io.vanja.ninja.repository.ServiceRepository
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Service
@Validated
class RmmService(
    private val serviceRepository: ServiceRepository,
    private val deviceRepository: DeviceRepository,
    private val cacheManager: CacheManager
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun createService(@Valid serviceCreateRequest: ServiceCreateRequest): io.vanja.ninja.domain.Service {
        try {
            return serviceRepository.saveAndFlush(
                io.vanja.ninja.domain.Service(
                    type = serviceCreateRequest.type,
                    price = serviceCreateRequest.price
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw AlreadyExistsException("Service already exists!")
        }
    }

    fun findService(id: Long): io.vanja.ninja.domain.Service? {
        return serviceRepository.findByIdOrNull(id) ?: throw DoesNotExistException("Service doesn't exist!")
    }

    @Transactional
    fun deleteService(id: Long) {
        try {
            serviceRepository.deleteById(id)
            serviceRepository.flush()
        } catch (e: DataIntegrityViolationException) {
            throw IllegalArgumentException("Service is used by device(s)")
        }
    }

    @Transactional
    fun createDevice(@Valid deviceCreateRequest: DeviceCreateRequest): Device {
        try {
            return deviceRepository.saveAndFlush(
                Device(
                    name = deviceCreateRequest.name,
                    type = deviceCreateRequest.type
                )
            )
        } catch (e: DataIntegrityViolationException) {
            throw AlreadyExistsException("Device already exists!")
        }
    }

    fun findDevice(id: Long): Device? {
        return deviceRepository.findByIdOrNull(id) ?: throw DoesNotExistException("Device doesn't exist!")
    }

    @Transactional
    fun deleteDevice(id: Long) {
        deviceRepository.deleteById(id)
        evictDeviceCostCache(id)
    }

    @Transactional
    fun addServiceToDevice(deviceId: Long, serviceId: Long) {
        try {
            deviceRepository.addServiceToDevice(deviceId, serviceId)
            evictDeviceCostCache(deviceId)
        } catch (e: DataIntegrityViolationException) {
            if (e.message!!.contains("is not present in table")) {
                throw DoesNotExistException("Service or device doesn't exist!")
            } else {
                throw AlreadyExistsException("Service already added to device")
            }
        }
    }

    @Transactional
    fun removeServiceFromDevice(deviceId: Long, serviceId: Long) {
        deviceRepository.removeServiceFromDevice(deviceId, serviceId)
        evictDeviceCostCache(deviceId)
    }

    private fun evictDeviceCostCache(deviceId: Long) {
        cacheManager.getCache("device-cost")?.evict(deviceId);
    }

    @Cacheable("device-cost")
    fun calculateDeviceCost(id: Long): Cost {
        return Cost(deviceRepository.deviceCost(id))
    }

    fun calculateTotalCost(): Cost {
        return Cost(deviceRepository.totalCost())
    }
}