import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.KubernetesResource
import com.zenika.tz.demo.deploy.Service
import com.zenika.tz.demo.deploy.ServiceType

KubernetesResource call(Map params, Closure body) {

    Service service = new Service()
    service.name = params.name
    service.type = ServiceType.valueOf(params.type)
    service.port = params.port
    service.targetPort = params.targetPort
    body()

    service.configure()

    PipelineContextHolder.kubernetes.addResource(service)

    return service
}