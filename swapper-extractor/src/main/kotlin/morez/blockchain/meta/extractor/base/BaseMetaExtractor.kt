package morez.blockchain.meta.extractor.base

import morez.blockchain.meta.model.SwapperMeta

abstract class BaseMetaExtractor {
    abstract fun fetchMeta(): SwapperMeta
}