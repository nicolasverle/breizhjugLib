import com.zenika.tz.demo.build.BuildContextEnum

import static com.zenika.tz.demo.PipelineContextHolder.buildContext

def call(Closure body) {
    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()
    }
}

def java(String target = "1.8") {
    buildContext = BuildContextEnum.JAVA
    node(buildContext.nodes) {
        checkout scm
        buildContext.strategy().build()
    }
}