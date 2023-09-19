package morez.blockchain.meta.connector.postgresql.entities

import jakarta.persistence.*

@Entity
@Table(name = "routes")
data class RouteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null,
    @Column(name = "swapper_id")
    var swapperId: Int?,
    @Column(name = "src_token_id")
    var srcTokenId: Int?,
    @Column(name = "src_token_id")
    var dstTokenId: Int?,
    @Column(name = "meta")
    var meta: String,
    @Column(name = "enabled")
    var enabled: Boolean = true
)