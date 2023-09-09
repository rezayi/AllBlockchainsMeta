package mrezaei.blockchains.meta.graph.nebula.connector.swappers.curve

import mrezaei.blockchains.meta.graph.nebula.connector.Blockchain
import mrezaei.blockchains.meta.graph.nebula.connector.Symbol
import mrezaei.blockchains.meta.graph.nebula.connector.Token
import mrezaei.blockchains.meta.graph.nebula.connector.TokenAddress

data class CurveFinanceConfig(
    val success: Boolean,
    val data: CurveFinanceData
)

data class CurveFinanceData(
    val poolData: List<CurveFinancePoolData>
)

data class CurveFinancePoolData(
    val address: TokenAddress,
    val lpTokenAddress: TokenAddress,
    val coins: List<CurveFinanceCoin>,
    val underlyingCoins: List<CurveFinanceCoin>?
) {
    fun toToken(blockchain: String): Token {
        TODO("Not yet implemented $blockchain")
    }
}

data class CurveFinanceCoin(
    val address: TokenAddress,
    val decimals: Int,
    val symbol: Symbol
) {
    fun toToken(blockchain: Blockchain) = Token(
        blockchain = blockchain,
        name = symbol,
        symbol = symbol,
        decimals = decimals,
        address = address
    )
}
