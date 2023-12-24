package dev.hyein.detectandalarmplugin

import org.elasticsearch.persistent.AllocatedPersistentTask
import org.elasticsearch.tasks.TaskId
import java.util.concurrent.ScheduledFuture


class CheckConditionsPersistentTask(id: Long, type: String, action: String, description: String?, parentTaskId: TaskId, headers: MutableMap<String, String>) :
    AllocatedPersistentTask(id, type, action, description, parentTaskId, headers) {// 노드 할당된 persistent task
        var future: ScheduledFuture<*>? = null

    /**
     * 취소 요청 들어오면
     * -future 로 작업 종료
     * -무사히 종료한 것으로 처리
     */
    override fun onCancelled() {
        super.onCancelled()
        future?.cancel(true)
        markAsCompleted()
    }
}
