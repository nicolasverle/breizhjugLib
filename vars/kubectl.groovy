import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(String namespace, Closure body) {

    PipelineContextHolder.kubernetes = new Kubernetes(namespace)
    body()
    PipelineContextHolder.kubernetes.apply()
}