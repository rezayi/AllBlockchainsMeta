package morez.blockchain.meta.connector.nebula

import com.vesoft.nebula.client.graph.SessionPool
import com.vesoft.nebula.client.graph.SessionPoolConfig
import com.vesoft.nebula.client.graph.data.HostAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class NebulaGraphConnectorService(
    @Value("\${nebula-graph.host}")
    host: String,
    @Value("\${nebula-graph.port}")
    port: Int,
    @Value("\${nebula-graph.username}")
    username: String,
    @Value("\${nebula-graph.password}")
    password: String,
    @Value("\${nebula-graph.space}")
    spaceName: String
) {
    final val sessionPool:SessionPool
    init {
        val addresses: List<HostAddress> = listOf(HostAddress(host, port))
        val sessionPoolConfig: SessionPoolConfig = SessionPoolConfig(addresses, spaceName, username, password)
            .setMaxSessionSize(10)
            .setMinSessionSize(10)
            .setWaitTime(100)
            .setRetryTimes(3)
            .setIntervalTime(100)
            .setReconnect(true)
        sessionPool = SessionPool(sessionPoolConfig)
        if (!sessionPool.init()) {
            error("session pool init failed.")
        }
    }
}