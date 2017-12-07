import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(Map params, Closure body) {

    PipelineContextHolder.kubernetes = new Kubernetes(params.namespace)
    body()
    PipelineContextHolder.kubernetes.apply()
}