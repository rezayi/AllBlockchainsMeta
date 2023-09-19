package morez.blockchain.meta.connector.postgresql.repositories

import morez.blockchain.meta.connector.postgresql.entities.BlockchainEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockchainRepository : CrudRepository<BlockchainEntity, Int>