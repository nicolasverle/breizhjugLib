import com.zenika.tz.demo.build.BuildContextEnum
import com.zenika.tz.demo.build.JavaBuildStrategy

import static com.zenika.tz.demo.PipelineContextHolder.buildContext

def call(Closure body) {
    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()
    }
}

def java() {
    buildContext = BuildContextEnum.JAVA
//    node(buildContext.getNodes()) {
//        checkout scm
//        new JavaBuildStrategy().build()
//    }
}