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

    @PostConstruct
    fun init() {
        insertToken("BTC", "Bitcoin", "", "BTC", 8)
        insertToken("ETH", "Ethereum", "", "ETH", 18)
        insertToken("ETH", "Wrapped Bitcoin", "0x2260FAC5E5542a773Aa44fBCfeDf7C193bc2C599", "WBTC", 18)
        insertToken("ETH", "Wrapped Ethereum", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "WETH", 18)

        insertBridge(name = "XY" , srcVid = "BTC_native", dstVid = "ETH_0x2260fac5e5542a773aa44fbcfedf7c193bc2c599")
        insertSwapper(name = "CurveFi" , srcVid = "ETH_native", dstVid = "ETH_0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2")
        insertSwapper(name = "CurveFi" , srcVid = "ETH_0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2", dstVid = "ETH_0x2260FAC5E5542a773Aa44fBCfeDf7C193bc2C599")
    }
}