package morez.blockchain.meta.main

import jakarta.annotation.PostConstruct
import morez.blockchain.meta.connector.nebula.GraphService
import morez.blockchain.meta.connector.postgresql.entities.RouteEntity
import morez.blockchain.meta.connector.postgresql.entities.SwapperEntity
import morez.blockchain.meta.connector.postgresql.entities.TokenEntity
import morez.blockchain.meta.connector.postgresql.repositories.RouteRepository
import morez.blockchain.meta.connector.postgresql.repositories.SwapperRepository
import morez.blockchain.meta.connector.postgresql.repositories.TokenRepository
import morez.blockchain.meta.extractor.base.BaseMetaExtractor
import morez.blockchain.meta.model.SwapperMeta
import org.springframework.stereotype.Service

@Service
class MetaUpdaterService(
    val graphService: GraphService,
    val swapperRepository: SwapperRepository,
    val tokenRepository: TokenRepository,
    val routeRepository: RouteRepository,
    val metaExtractors: List<BaseMetaExtractor>
) {
    @PostConstruct
    fun init() {
        metaExtractors
            .forEach { metaExtractor ->
                val meta = metaExtractor.fetchMeta()
                persistMeta(meta)
            }
    }

    private fun persistMeta(swapper: SwapperMeta) {
        val swapperEntity = swapperRepository.save(
            SwapperEntity(
                name = swapper.name,
                type = swapper.type,
            )
        )
        swapper.tokens.forEach { token ->
            val tokenEntity = tokenRepository.save(
                TokenEntity(
                    blockchain = token.blockchain,
                    name = token.name,
                    symbol = token.symbol,
                    address = token.address,
                    decimals = token.decimals
                )
            )
            token.id = tokenEntity.id
        }
        swapper.routes
            .forEach { route ->
                routeRepository.save(
                    RouteEntity(
                        swapperId = swapperEntity.id,
                        srcTokenId = route.srcToken.id,
                        dstTokenId = route.dstToken.id,
                        meta = route.routeMetaId,

                        )
                )
            }
    }
}