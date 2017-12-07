import com.zenika.tz.demo.PipelineContextHolder

def call(Map params, Closure body) {

    PipelineContextHolder.deployContext.appName = params.appName
    PipelineContextHolder.deployContext.port = params.appPort

    node("master") {
        stage("Deploying application") {
            if(body) {
                body.resolveStrategy = Closure.DELEGATE_FIRST
                body.delegate = this
                body()
            }
        }
    }


}