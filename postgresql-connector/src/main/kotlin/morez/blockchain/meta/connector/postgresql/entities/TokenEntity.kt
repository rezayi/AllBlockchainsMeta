package morez.blockchain.meta.connector.postgresql.entities

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
data class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(name = "blockchain_id")
    var blockchainId: Int,
    @Column(name = "name")
    var name: String,
    @Column(name = "symbol")
    var symbol: String,
    @Column(name = "address")
    var address: String,
    @Column(name = "decimals")
    var decimals: Int,
    @Column(name = "enabled")
    var enabled: Boolean = true
)