package morez.blockchain.meta.connector.postgresql.repositories

import morez.blockchain.meta.connector.postgresql.entities.TokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : CrudRepository<TokenEntity, Int>