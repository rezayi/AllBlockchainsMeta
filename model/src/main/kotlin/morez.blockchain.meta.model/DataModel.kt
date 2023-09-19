package morez.blockchain.meta.model

typealias TokenAddress = String
typealias Symbol = String

data class Token(
    val blockchain: Blockchain,
    val name: String,
    val symbol: Symbol,
    val decimals: Int,
    val address: TokenAddress
) {
    var id: Int? = null
}

data class Route(
    val srcToken: Token,
    val dstToken: Token,
    val routeMetaId: String,
    val routeInfo: Map<String, String>
)

data class SwapperMeta(
    val name: String,
    val type: SwapperType,
    val tokens: List<Token>,
    val routes: List<Route>
)

enum class SwapperType {
    Swapper, Bridge
}