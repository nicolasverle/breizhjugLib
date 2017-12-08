import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(Map params, Closure body) {

    echo("Deploying ${PipelineContextHolder.deployContext.appName} on port ${PipelineContextHolder.deployContext.appPort}")
    Kubernetes kubernetes = new Kubernetes(params.namespace)
    PipelineContextHolder.kubernetes = kubernetes

    try {
        kubernetes.initContext()
        if(params.manualValidation) {
            kubernetes.manualValidation = true
        }
        body()
        PipelineContextHolder.kubernetes.apply()
    } catch (err) {
        error(err.getMessage())
    }

}