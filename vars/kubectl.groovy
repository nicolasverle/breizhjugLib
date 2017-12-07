import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(Map params, Closure body) {

    echo("instanciate Kubernetes")
    PipelineContextHolder.kubernetes = new Kubernetes(params.namespace)
    echo("calling body()")
    body()
    PipelineContextHolder.kubernetes.apply()
}