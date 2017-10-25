import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.build.JavaBuildStrategy

def call(Closure body) {
    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()
    }
}

def java() {
    PipelineContextHolder.buildStrategy = new JavaBuildStrategy()
    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        stage("Building java sources") {
            checkout scm
            PipelineContextHolder.buildStrategy.build()
        }
    }
}