package dev.hyein

import org.elasticsearch.action.ActionListener
import org.elasticsearch.action.support.ActionFilters
import org.elasticsearch.action.support.TransportAction
import org.elasticsearch.common.inject.Inject
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.tasks.Task
import org.elasticsearch.transport.TransportService

/**
 * 실제로 액션이 실행될 때 수행되야할 동작 구현
 */
class TransportHelloWorldAction @Inject constructor( // @Inject: 구글 Guice 애노테이션. 생성자를 통해 의존성 주입 가능
    actionFilters: ActionFilters, transportService: TransportService,
    private val settings: Settings
) : TransportAction<HelloWorldRequest, HelloWorldResponse>(
    NAME, actionFilters, transportService.taskManager) {

    override fun doExecute(task: Task, request: HelloWorldRequest, listener: ActionListener<HelloWorldResponse>) {
        try {
            val greetings = settings.get("hello.greetings", "hello") // ES 에서 설정한 hello.greetings 값을 읽음
            listener.onResponse(HelloWorldResponse(greetings, request.name)) // 설정값 greetings 와 URL 파라미터 name 으로 응답할 response 생성
        } catch (e: Exception) {
            listener.onFailure(e)
        }
    }

}
