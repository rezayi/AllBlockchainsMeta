package mrezaei.blockchains.meta.graph.nebula.connector.swappers.curve

import jakarta.annotation.PostConstruct
import mrezaei.blockchains.meta.graph.nebula.connector.Token
import mrezaei.blockchains.meta.graph.nebula.connector.TokenAddress
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CurveFinanceTokenExtractor(
    val restTemplate: RestTemplate
) {
    companion object {
        private const val URL = "https://api.curve.fi/api/getPools/polygon/main"
        private const val BLOCKCHAIN = "polygon"
    }

    @PostConstruct
    fun loadAPI() {
        val tokensMap: MutableMap<TokenAddress, Token> = mutableMapOf()

        val config = restTemplate.getForObject(URL, CurveFinanceConfig::class.java)
        config?.data?.poolData
            ?.flatMap { pool ->
                val lpToken = tokensMap.getOrPut(pool.lpTokenAddress) {
                    pool.toToken(BLOCKCHAIN)
                }
                listOf(
                    getTokenToTokenPairs(pool.coins, tokensMap),
                    getTokenToTokenPairs(pool.underlyingCoins.orEmpty(), tokensMap),
                    pool.coins.flatMap { token ->
                        val memberToken = tokensMap.getOrPut(token.address) {
                            token.toToken(BLOCKCHAIN)
                        }
                        listOf(
                            lpToken to memberToken,
                            memberToken to lpToken
                        )
                    }
                ).flatten()
            }
    }

    private fun getTokenToTokenPairs(
        coins: List<CurveFinanceCoin>,
        tokensMap: MutableMap<TokenAddress, Token>
    ) = coins
        .flatMap { src ->
            val srcToken = tokensMap.getOrPut(src.address) {
                src.toToken(BLOCKCHAIN)
            }
            coins
                .filter { dst -> dst != src }
                .map { dst ->
                    val dstToken = tokensMap.getOrPut(dst.address) {
                        dst.toToken(BLOCKCHAIN)
                    }

                    srcToken to dstToken
                }
        }


}