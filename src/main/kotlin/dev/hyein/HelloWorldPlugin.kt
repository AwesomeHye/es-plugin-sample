package dev.hyein

import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.ActionResponse
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver
import org.elasticsearch.cluster.node.DiscoveryNodes
import org.elasticsearch.common.settings.*
import org.elasticsearch.logging.LogManager
import org.elasticsearch.plugins.ActionPlugin
import org.elasticsearch.plugins.Plugin
import org.elasticsearch.rest.RestController
import org.elasticsearch.rest.RestHandler
import org.elasticsearch.rest.RestHeaderDefinition
import java.util.function.Supplier

class HelloWorldPlugin(private val settings: Settings) : Plugin(), ActionPlugin { // 인터페이스는 괄호 안붙이나봄
    private val log = LogManager.getLogger(javaClass)
    init {
        log.info("HelloWorldPlugin init")
    }

    override fun getSettings(): MutableList<Setting<*>> {
        return mutableListOf(
            Setting.simpleString("hello.greetings", Setting.Property.Dynamic, Setting.Property.NodeScope)
        )
    }

    override fun getActions(): MutableList<ActionPlugin.ActionHandler<out ActionRequest, out ActionResponse>> {
        return mutableListOf(ActionPlugin.ActionHandler(HelloWorldAction, TransportHelloWorldAction::class.java))
    }

    override fun getRestHandlers(
        settings: Settings?,
        restController: RestController?,
        clusterSettings: ClusterSettings?,
        indexScopedSettings: IndexScopedSettings?,
        settingsFilter: SettingsFilter?,
        indexNameExpressionResolver: IndexNameExpressionResolver?,
        nodesInCluster: Supplier<DiscoveryNodes>?
    ): MutableList<RestHandler> {
        return mutableListOf(RestHelloWorldAction())
    }
}
