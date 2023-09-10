package morez.blockchain.meta.connector.nebula

import com.vesoft.nebula.client.graph.data.ResultSet
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
            "INSERT VERTEX IF NOT EXISTS Token (name,blockchain,address,symbol,decimals,enable) VALUES \"$id\":(\"$name\",\"$blockchain\",\"${address.lowercase()}\",\"$symbol\",$decimals,$enable)"
        )
    }

    fun insertSwapper(
        name: String,
        enable: Boolean = true,
        srcVid: String,
        dstVid: String
    ): ResultSet {
        return connector.sessionPool.execute(
            "INSERT EDGE IF NOT EXISTS Swapper (name,enable) VALUES \"$srcVid\" -> \"$dstVid\" :(\"$name\",$enable) "
        )
    }

    fun insertBridge(
        name: String,
        enable: Boolean = true,
        srcVid: String,
        dstVid: String
    ): ResultSet {
        return connector.sessionPool.execute(
            "INSERT EDGE IF NOT EXISTS Bridge (name,enable) VALUES \"$srcVid\" -> \"$dstVid\" :(\"$name\",$enable) "
        )
    }
}