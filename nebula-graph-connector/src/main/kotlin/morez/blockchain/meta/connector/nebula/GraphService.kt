package morez.blockchain.meta.connector.nebula

import com.vesoft.nebula.client.graph.data.ResultSet
import morez.blockchain.meta.model.Route
import morez.blockchain.meta.model.Token
import org.springframework.stereotype.Service

@Service
class GraphService(
    val connector: NebulaGraphConnectorService
) {

    fun insertToken(
        token: Token,
    ): ResultSet {
        val id = "${token.blockchain}_${if (token.address.isEmpty()) "native" else token.address.lowercase()}"
        return connector.sessionPool.execute(
            "INSERT VERTEX IF NOT EXISTS Token (name,blockchain,address,symbol,decimals) VALUES \"$id\":(\"${token.name}\",\"${token.blockchain}\",\"${token.address.lowercase()}\",\"${token.symbol}\",${token.decimals})"
        )
    }

    fun insertSwapper(
        route: Route
    ): ResultSet {
        val srcVid = "${route.srcToken.blockchain}_${if (route.srcToken.address.isEmpty()) "native" else route.srcToken.address.lowercase()}"
        val dstVid = "${route.dstToken.blockchain}_${if (route.dstToken.address.isEmpty()) "native" else route.dstToken.address.lowercase()}"
        return connector.sessionPool.execute(
            "INSERT EDGE IF NOT EXISTS Swapper (name,meta-id) VALUES \"$srcVid\" -> \"$dstVid\" :(\"${route.swapperName},${route.routeMetaId}\") "
        )
    }

    fun insertBridge(
        name: String,
        srcVid: String,
        dstVid: String,
        enable: Boolean = true
    ): ResultSet {
        return connector.sessionPool.execute(
            "INSERT EDGE IF NOT EXISTS Bridge (name,enable) VALUES \"$srcVid\" -> \"$dstVid\" :(\"$name\",$enable) "
        )
    }
}