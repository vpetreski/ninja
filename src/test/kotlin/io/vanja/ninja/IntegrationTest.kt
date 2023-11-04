package io.vanja.ninja

import io.vanja.ninja.data.DeviceCreateRequest
import io.vanja.ninja.data.ServiceCreateRequest
import io.vanja.ninja.data.ServiceDeviceRequest
import io.vanja.ninja.exception.AlreadyExistsException
import io.vanja.ninja.service.RmmService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class IntegrationTest(
    @Autowired val rmmService: RmmService,
) : BaseIntegrationTest() {
    @Test
    fun `Test RMM`() {
        val service1 = rmmService.createService(ServiceCreateRequest(type = "Foo", price = 3L))

        Assertions.assertThatExceptionOfType(AlreadyExistsException::class.java).isThrownBy {
            rmmService.createService(ServiceCreateRequest(type = "Foo", price = 3L))
        }.withMessage("Service already exists!")

        val service2 = rmmService.createService(ServiceCreateRequest(type = "Bar", price = 2L))

        val device1 = rmmService.createDevice(DeviceCreateRequest("Gamer", type = "Windows"))

        Assertions.assertThatExceptionOfType(AlreadyExistsException::class.java).isThrownBy {
            rmmService.createDevice(DeviceCreateRequest("Gamer", type = "Windows"))
        }.withMessage("Device already exists!")

        val device2 = rmmService.createDevice(DeviceCreateRequest("Developer", type = "Mac"))

        rmmService.addServiceToDevice(device1.id!!, ServiceDeviceRequest(service1.id!!))
        rmmService.addServiceToDevice(device1.id!!, ServiceDeviceRequest(service2.id!!))
        rmmService.addServiceToDevice(device2.id!!, ServiceDeviceRequest(service2.id!!))

        assertThat(rmmService.calculateTotalCost()!!.cost).isEqualTo(service1.price!! + service2.price!! * 2)
    }
}