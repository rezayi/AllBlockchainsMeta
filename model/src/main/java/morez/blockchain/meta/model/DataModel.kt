package morez.blockchain.meta.model

typealias TokenAddress = String
typealias Blockchain = String
typealias Symbol = String

data class Token(
    val blockchain: Blockchain,
    val name: String,
    val symbol: Symbol,
    val decimals: Int,
    val address: TokenAddress
)

data class Route(
    val srcToken: Token,
    val dstToken: Token,
    val swapperName: String,
    val routeInfo: Map<String, String>
)

data class SwapperMeta(
    val tokens: List<Token>,
    val routes: List<Route>
)