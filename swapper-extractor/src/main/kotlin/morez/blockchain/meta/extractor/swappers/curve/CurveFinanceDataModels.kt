package morez.blockchain.meta.extractor.swappers.curve

import morez.blockchain.meta.model.Blockchain
import morez.blockchain.meta.model.Symbol
import morez.blockchain.meta.model.Token
import morez.blockchain.meta.model.TokenAddress

data class CurveFinanceConfig(
    val success: Boolean,
    val data: CurveFinanceData
)

data class CurveFinanceData(
    val poolData: List<CurveFinancePoolData>
)

data class CurveFinancePoolData(
    val id: String,
    val name: String,
    val symbol: Symbol,
    val address: TokenAddress,
    val lpTokenAddress: TokenAddress,
    val coins: List<CurveFinanceCoin>,
    val underlyingCoins: List<CurveFinanceCoin>?
) {
    fun toToken(blockchain: Blockchain) = Token(
        blockchain = blockchain,
        name = name,
        symbol = symbol,
        decimals = coins.maxOf { coin -> coin.decimals },//TODO check if its true
        address = lpTokenAddress
    )
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
