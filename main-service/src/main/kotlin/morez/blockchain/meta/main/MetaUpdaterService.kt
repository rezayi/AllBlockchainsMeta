package morez.blockchain.meta.main

import jakarta.annotation.PostConstruct
import morez.blockchain.meta.connector.nebula.GraphService
import morez.blockchain.meta.extractor.base.BaseMetaExtractor
import morez.blockchain.meta.model.SwapperMeta
import org.springframework.stereotype.Service

@Service
class MetaUpdaterService(
    val graphService: GraphService,
    val metaExtractors: List<BaseMetaExtractor>
) {
    @PostConstruct
    fun init() {
        metaExtractors
            .forEach { metaExtractor ->
                val meta=metaExtractor.fetchMeta()
                persistMeta(meta)
            }
    }

    private fun persistMeta(meta: SwapperMeta) {
        TODO("Not yet implemented")
        //        graphService.insertToken("BTC", "Bitcoin", "", "BTC", 8)
//        graphService.insertToken("ETH", "Ethereum", "", "ETH", 18)
//        graphService.insertToken("ETH", "Wrapped Bitcoin", "0x2260FAC5E5542a773Aa44fBCfeDf7C193bc2C599", "WBTC", 18)
//        graphService.insertToken("ETH", "Wrapped Ethereum", "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2", "WETH", 18)
//
//        graphService.insertBridge(
//            name = "XY",
//            srcVid = "BTC_native",
//            dstVid = "ETH_0x2260fac5e5542a773aa44fbcfedf7c193bc2c599"
//        )
//        graphService.insertSwapper(
//            name = "CurveFi",
//            srcVid = "ETH_native",
//            dstVid = "ETH_0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2"
//        )
//        graphService.insertSwapper(
//            name = "CurveFi",
//            srcVid = "ETH_0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2",
//            dstVid = "ETH_0x2260FAC5E5542a773Aa44fBCfeDf7C193bc2C599"
//        )
    }
}