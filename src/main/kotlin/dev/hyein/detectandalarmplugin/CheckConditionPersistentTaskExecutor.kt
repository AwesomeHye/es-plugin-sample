package dev.hyein.detectandalarmplugin

import org.elasticsearch.client.internal.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.logging.LogManager
import org.elasticsearch.persistent.AllocatedPersistentTask
import org.elasticsearch.persistent.PersistentTaskState
import org.elasticsearch.persistent.PersistentTasksCustomMetadata
import org.elasticsearch.persistent.PersistentTasksExecutor
import org.elasticsearch.tasks.TaskId
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

public const val TASK_NAME = "detect-and-alarm-task" // _task 이름

class CheckConditionPersistentTaskExecutor(
    executor: String,
    settings: Settings,
    private val client: Client
) : PersistentTasksExecutor<CheckConditionPersistentTaskParams>(TASK_NAME, executor){
    private val log = LogManager.getLogger(javaClass)
    private val scheduledExecutorService = Executors.newScheduledThreadPool(10)

    private val alarmUrl = settings.get("detect.alarm.url")
    private val alarmEmail = settings.get("detect.alarm.email")
    private val timeoutSec = settings.getAsLong("detect.condition.timeoutSec", 30L)

    /**
     * 오버라이드해서 기본 AllocatedPersistentTask 가 아닌 CheckConditionsPersistentTask 생성하게 함
     */
    override fun createTask(
        id: Long,
        type: String,
        action: String,
        parentTaskId: TaskId,
        taskInProgress: PersistentTasksCustomMetadata.PersistentTask<CheckConditionPersistentTaskParams>,
        headers: MutableMap<String, String>
    ): AllocatedPersistentTask {
        headers["alarmUrl"] = alarmUrl
        headers["alarmEmail"] = alarmEmail
        headers["timeoutSec"] = timeoutSec.toString()

        return CheckConditionsPersistentTask(id, type, action, getDescription(taskInProgress), parentTaskId, headers)
    }

    override fun nodeOperation(task: AllocatedPersistentTask, // 위에 createTask 에서 생성한 task
                               params: CheckConditionPersistentTaskParams,
                               state: PersistentTaskState?) {
        task.headers()["fixedDelaySec"] = params.fixedDelaySec.toString()

        // 반복 작업 등록
        val future = scheduledExecutorService.scheduleWithFixedDelay(detectAndAlarmRunnable(params), 1L, params.fixedDelaySec, TimeUnit.SECONDS)
        (task as? CheckConditionsPersistentTask)?.future = future
    }

    private fun detectAndAlarmRunnable(params: CheckConditionPersistentTaskParams): Runnable {
        TODO("Not yet implemented")
    }
}
