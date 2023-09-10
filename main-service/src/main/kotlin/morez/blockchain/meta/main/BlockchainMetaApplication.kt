package morez.blockchain.meta.main

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("morez.blockchain.meta")
class BlockchainMetaApplication

fun main(args: Array<String>) {
    runApplication<BlockchainMetaApplication>(*args)
}
