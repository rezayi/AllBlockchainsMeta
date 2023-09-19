package morez.blockchain.meta.main

import jakarta.annotation.PostConstruct
import morez.blockchain.meta.connector.nebula.GraphService
import morez.blockchain.meta.connector.postgresql.entities.RouteEntity
import morez.blockchain.meta.connector.postgresql.entities.SwapperEntity
import morez.blockchain.meta.connector.postgresql.entities.TokenEntity
import morez.blockchain.meta.connector.postgresql.repositories.BlockchainRepository
import morez.blockchain.meta.connector.postgresql.repositories.RouteRepository
import morez.blockchain.meta.connector.postgresql.repositories.SwapperRepository
import morez.blockchain.meta.connector.postgresql.repositories.TokenRepository
import morez.blockchain.meta.extractor.base.BaseMetaExtractor
import morez.blockchain.meta.model.Blockchain
import morez.blockchain.meta.model.SwapperMeta
import org.springframework.stereotype.Service

@Service
class MetaUpdaterService(
    private val graphService: GraphService,
    private val blockchainRepository: BlockchainRepository,
    private val swapperRepository: SwapperRepository,
    private val tokenRepository: TokenRepository,
    private val routeRepository: RouteRepository,
    private val metaExtractors: List<BaseMetaExtractor>
) {
    @PostConstruct
    fun init() {
        val blockchains = blockchainRepository.findAll()
            .associate { entity -> Blockchain.valueOf(entity.name) to entity.id }

        metaExtractors
            .forEach { metaExtractor ->
                val meta = metaExtractor.fetchMeta()
                persistMeta(meta, blockchains)
            }
    }

    private fun persistMeta(swapper: SwapperMeta, blockchains: Map<Blockchain, Int?>) {
        val swapperEntity = swapperRepository.save(
            SwapperEntity(
                name = swapper.name,
                type = swapper.type,
            )
        )
        swapper.tokens.forEach { token ->
            val tokenEntity = tokenRepository.save(
                TokenEntity(
                    blockchainId = blockchains[token.blockchain] ?: throw InvalidBlockchainException(),
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