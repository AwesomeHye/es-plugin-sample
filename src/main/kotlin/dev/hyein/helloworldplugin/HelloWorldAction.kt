package dev.hyein.helloworldplugin

import org.elasticsearch.action.ActionType

const val NAME: String = "cluster:manage/example/helloworld" // 액션 이름. 빌트인 공식 액션 이름 참고해서 네이밍 컨벤션 지키기

/**
 * ActionType 을 싱글톤으로 정의
 * object 로 선언하면 싱글톤
 */
object HelloWorldAction : ActionType<HelloWorldResponse>(NAME, ::HelloWorldResponse)
