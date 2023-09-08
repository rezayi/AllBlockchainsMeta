package mrezaei.blockchains.meta.graph.nebula.connector

import com.vesoft.nebula.client.graph.data.ResultSet
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class GraphService(
    val connector: NebulaGraphConnectorService
) {

    fun insertToken(
        blockchain: String,
        name: String,
        address: String,
        symbol: String,
        decimals: Int,
        enable: Boolean = true
    ): ResultSet {
        val id = "${blockchain}_${if (address.isEmpty()) "native" else address.lowercase()}"
        return connector.sessionPool.execute(
            "INSERT VERTEX IF NOT EXISTS Token (blockchain,address,symbol,decimals,enable) VALUES \"$id\":(\"$blockchain\",\"$address\",\"$symbol\",$decimals,$enable)"
        )
    }

    @PostConstruct
    fun init(){
        insertToken("BTC","BTC","","BTC",8)
    }
}