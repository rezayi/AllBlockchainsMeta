package morez.blockchain.meta.connector.postgresql.entities

import jakarta.persistence.*
import morez.blockchain.meta.model.SwapperType

@Entity
@Table(name = "swappers")
data class SwapperEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(name = "name")
    var name: String,
    @Column(name = "type")
    var type: SwapperType,
    @Column(name = "enabled")
    var enabled: Boolean = true
)

