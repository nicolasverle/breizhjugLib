import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Ingress

def call(String host, Closure body) {

    Ingress ingress = new Ingress(host)
    ingress.configure()
    body()
    PipelineContextHolder.kubernetes.addResource(ingress)

}