package dev.hyein.detectandalarmplugin

import org.elasticsearch.client.internal.Client
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver
import org.elasticsearch.cluster.service.ClusterService
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.settings.SettingsModule
import org.elasticsearch.persistent.PersistentTasksExecutor
import org.elasticsearch.plugins.ActionPlugin
import org.elasticsearch.plugins.PersistentTaskPlugin
import org.elasticsearch.plugins.Plugin
import org.elasticsearch.threadpool.ThreadPool

class DetectAndAlarmPlugin(private val settings: Settings) : Plugin(), ActionPlugin, PersistentTaskPlugin {
    /**
     * ES 에 PersistentTaskExecutor 를 등록
     */
    override fun getPersistentTasksExecutor(
        clusterService: ClusterService,
        threadPool: ThreadPool,
        client: Client,
        settingsModule: SettingsModule,
        expressionResolver: IndexNameExpressionResolver
    ): MutableList<PersistentTasksExecutor<*>> {
        return mutableListOf(CheckConditionPersistentTaskExecutor(ThreadPool.Names.SEARCH, settings, client))
    }
}
