import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.build.JavaBuildStrategy

def call(Map params = null, Closure body) {

    setContext()

    if(params) {
        if(params.verbose == true) {
            PipelineContextHolder.verbose = true
        }
    }

    try {
        if(body) {
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            body()
        }
    } catch (err) {
        error(err.getMessage())
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