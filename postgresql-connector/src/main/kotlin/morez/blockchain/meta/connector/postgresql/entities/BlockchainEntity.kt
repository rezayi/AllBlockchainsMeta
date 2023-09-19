package morez.blockchain.meta.connector.postgresql.entities

import jakarta.persistence.*

@Entity
@Table(name = "blockchains")
data class BlockchainEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: Int?,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "type", nullable = false)
    val type: String,
    @Column(name = "enabled", nullable = false)
    val enabled: Boolean = true
)