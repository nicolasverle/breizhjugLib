import com.zenika.tz.demo.PipelineContextHolder

def call(Map params, Closure body) {

    setContext()

    PipelineContextHolder.deployContext.appName = params.appName
    PipelineContextHolder.deployContext.appPort = params.appPort
    if(params.verbose) {
        PipelineContextHolder.verbose = true
    }

    node("master") {
        stage("Deploying application") {
            if(body) {
                try {
                    body.resolveStrategy = Closure.DELEGATE_FIRST
                    body.delegate = this
                    body()
                } catch(err) {
                    error(err.getMessage())
                }
            }
        }
    }


}