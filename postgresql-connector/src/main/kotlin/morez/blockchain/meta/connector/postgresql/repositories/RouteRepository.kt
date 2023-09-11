package morez.blockchain.meta.connector.postgresql.repositories

import morez.blockchain.meta.connector.postgresql.entities.RouteEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteRepository : CrudRepository<RouteEntity, Int>