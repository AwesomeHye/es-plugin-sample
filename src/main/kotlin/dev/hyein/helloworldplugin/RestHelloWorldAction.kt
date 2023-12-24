package dev.hyein.helloworldplugin

import org.elasticsearch.action.ActionListener
import org.elasticsearch.client.internal.node.NodeClient
import org.elasticsearch.rest.BaseRestHandler
import org.elasticsearch.rest.RestHandler
import org.elasticsearch.rest.RestRequest
import java.lang.Exception

/**
 * 액션을 ES REST API 에 등록시킨다
 */
class RestHelloWorldAction : BaseRestHandler() {
    override fun routes(): MutableList<RestHandler.Route> = mutableListOf(RestHandler.Route(RestRequest.Method.GET, "/_hello"))

    override fun getName(): String = "hello_world_action" // GET /_nodes/usage 에서 사용

    /**
     * ActionRequest 생성, ActionResponse 를 RestResponse 로 변환
     */
    override fun prepareRequest(request: RestRequest, client: NodeClient): RestChannelConsumer {
        // ActionRequest 만들기
        val name = request.param("name", "world")
        val actionRequest = HelloWorldRequest(name)

        // 액션 요청, 수행, 응답 사이 연결
        return RestChannelConsumer { channel ->
            client.execute(HelloWorldAction, actionRequest, object : ActionListener<HelloWorldResponse> {
            override fun onResponse(response: HelloWorldResponse) {
                channel.sendResponse(DefaultRestResponse.success(response, channel))
            }

            override fun onFailure(e: Exception) {
                channel.sendResponse(DefaultRestResponse.error(e))
            }
        })
        }
    }

}
