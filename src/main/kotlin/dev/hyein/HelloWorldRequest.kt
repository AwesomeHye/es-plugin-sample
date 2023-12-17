package dev.hyein

import org.elasticsearch.action.ActionRequest
import org.elasticsearch.action.ActionRequestValidationException
import org.elasticsearch.common.io.stream.StreamInput
import org.elasticsearch.common.io.stream.StreamOutput

class HelloWorldRequest : ActionRequest {
    val name: String

    // ActionRequest 도 생성자 2개임. 그래서 2개 구현해줌
    constructor(name: String) : super() {
        this.name = name
    }

    constructor(input: StreamInput) : super(input) {
        this.name = input.readString()
    }

    /**
     * 직렬화, 역직렬화에 사용
     */
    override fun writeTo(out: StreamOutput) {
        super.writeTo(out)
        out.writeString(name)
    }

    override fun validate(): ActionRequestValidationException? {
        return if (name.isEmpty()) {
            val validationException = ActionRequestValidationException()
            validationException.addValidationError("name must not be empty.")
            validationException
        } else {
            null // 반환값이 null 이 아니면 검증 실패로 간주하고 요청 수행하지 않음
        }
    }
}
