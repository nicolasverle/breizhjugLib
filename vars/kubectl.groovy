import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(Map params, Closure body) {

    try {
        echo("Deploying ${PipelineContextHolder.deployContext.appName} with port ${PipelineContextHolder.deployContext.appPort}")
        Kubernetes kubernetes = new Kubernetes(params.namespace)
        PipelineContextHolder.kubernetes = kubernetes
        kubernetes.initContext()
        body()
        PipelineContextHolder.kubernetes.apply()
    } catch (err) {
        error(err.dump())
    }

}