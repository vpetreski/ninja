package io.vanja.ninja.repository;

import io.vanja.ninja.domain.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface DeviceRepository : JpaRepository<Device, Long> {
    @Modifying
    @Query(value = "INSERT INTO ninja_device_service (device_id, service_id) VALUES (?, ?)", nativeQuery = true)
    fun addServiceToDevice(deviceId: Long, serviceId: Long)

    @Modifying
    @Query(value = "DELETE FROM ninja_device_service WHERE device_id = ? AND service_id = ?", nativeQuery = true)
    fun removeServiceFromDevice(deviceId: Long, serviceId: Long)

    @Query(value = "SELECT SUM(price) FROM ninja_device_service INNER JOIN ninja_service ON service_id = id WHERE device_id = ?", nativeQuery = true)
    fun deviceCost(deviceId: Long): Long

    @Query(value = "SELECT SUM(price) FROM ninja_device_service INNER JOIN ninja_service ON service_id = id", nativeQuery = true)
    fun totalCost(): Long
}