import com.zenika.tz.demo.PipelineContextHolder

def call(Map params, Closure body) {

    try {
        //Kubernetes kubernetes = new Kubernetes(params.namespace)
        //PipelineContextHolder.kubernetes = kubernetes
        //kubernetes.initContext()
        //body()
        //PipelineContextHolder.kubernetes.apply()
    } catch (err) {
        error(err.dump())
    }

}