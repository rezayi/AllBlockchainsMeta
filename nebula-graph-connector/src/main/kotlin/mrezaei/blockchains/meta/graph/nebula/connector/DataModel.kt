package mrezaei.blockchains.meta.graph.nebula.connector

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