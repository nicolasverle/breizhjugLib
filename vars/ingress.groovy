import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Ingress

def call(String host, Closure body) {

    Ingress ingress = new Ingress(host)
    try {
        body()
        ingress.configure()
        PipelineContextHolder.kubernetes.addResource(ingress)
    } catch (err) {
        error(err.getMessage())
    }

}