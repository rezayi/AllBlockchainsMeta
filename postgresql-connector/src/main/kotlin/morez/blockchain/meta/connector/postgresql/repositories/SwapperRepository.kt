package morez.blockchain.meta.connector.postgresql.repositories

import morez.blockchain.meta.connector.postgresql.entities.SwapperEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SwapperRepository : CrudRepository<SwapperEntity, Int>