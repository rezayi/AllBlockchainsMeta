package mrezaei.blockchains.meta.graph.nebula.connector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class NebulaGraphConnectorApplication {
    @Bean
    fun restTemplate() = RestTemplate()
}

fun main(args: Array<String>) {
    runApplication<NebulaGraphConnectorApplication>(*args)
}
