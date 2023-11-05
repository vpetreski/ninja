package io.vanja.ninja

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class BaseIntegrationTest {
    companion object {
        @Container
        private val dbContainer = PostgreSQLContainer<Nothing>("postgres:16.0-alpine").apply {
            start()
        }

        private val cacheContainer = GenericContainer("redis:7.2.3-alpine").withExposedPorts(6379).apply {
            start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { "${dbContainer.jdbcUrl}&stringtype=unspecified" }
            registry.add("spring.datasource.username", dbContainer::getUsername)
            registry.add("spring.datasource.password", dbContainer::getPassword)
            registry.add("spring.data.redis.host", cacheContainer::getHost)
            registry.add("spring.data.redis.port") { cacheContainer.getMappedPort(6379).toString() }
        }
    }
}