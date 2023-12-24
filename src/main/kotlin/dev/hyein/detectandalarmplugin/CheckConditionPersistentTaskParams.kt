package dev.hyein.detectandalarmplugin

import org.elasticsearch.Version
import org.elasticsearch.common.io.stream.StreamInput
import org.elasticsearch.common.io.stream.StreamOutput
import org.elasticsearch.persistent.PersistentTaskParams
import org.elasticsearch.xcontent.ParseField
import org.elasticsearch.xcontent.ToXContent
import org.elasticsearch.xcontent.XContentBuilder

class CheckConditionPersistentTaskParams : PersistentTaskParams {
    val fixedDelaySec: Long

    constructor(fixedDelaySec: Long) {
        this.fixedDelaySec = fixedDelaySec
    }

    constructor(input: StreamInput) {
        this.fixedDelaySec = input.readLong()
    }


    override fun writeTo(out: StreamOutput) {
        out.writeLong(fixedDelaySec)
    }

    override fun getWriteableName(): String = TASK_NAME

    override fun getMinimalSupportedVersion(): Version = Version.V_7_0_0

    override fun toXContent(builder: XContentBuilder, params: ToXContent.Params): XContentBuilder {
        builder.startObject()
        builder.field(FIXED_DELAY_SEC_FIELD_NAME, fixedDelaySec)
        builder.endObject()
        return builder
    }

    // static 영역
    companion object {
        const val TASK_NAME = "detect-and-alarm-task"
        const val FIXED_DELAY_SEC_FIELD_NAME = "fixedDelaySec"
        private val FIXED_DELAY_SEC_FIELD = ParseField(FIXED_DELAY_SEC_FIELD_NAME)

        //...
    }
}
