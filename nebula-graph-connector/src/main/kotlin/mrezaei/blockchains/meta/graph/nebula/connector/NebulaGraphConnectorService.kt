package mrezaei.blockchains.meta.graph.nebula.connector

import com.vesoft.nebula.client.graph.SessionPool
import com.vesoft.nebula.client.graph.SessionPoolConfig
import com.vesoft.nebula.client.graph.data.HostAddress
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class NebulaGraphConnectorService(
    @Value("\${nebula-graph.host}")
    private val host: String,
    @Value("\${nebula-graph.port}")
    private val port: Int,
    @Value("\${nebula-graph.username}")
    private val username: String,
    @Value("\${nebula-graph.password}")
    private val password: String,
    @Value("\${nebula-graph.space}")
    private val spaceName: String
) {
    lateinit var sessionPool:SessionPool
    @PostConstruct
    fun init() {
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