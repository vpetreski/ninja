package io.vanja.ninja

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class NinjaApplication

fun main(args: Array<String>) {
    runApplication<NinjaApplication>(*args)
}
