import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Kubernetes

def call(Map params, Closure body) {

    try {
        echo("Into kubectl with args ${params.dump()}")
        Kubernetes kubernetes = new Kubernetes(params.namespace)
        PipelineContextHolder.kubernetes = kubernetes
        kubernetes.initContext()
        body()
        //PipelineContextHolder.kubernetes.apply()
    } catch (err) {
        error(err.dump())
    }

}