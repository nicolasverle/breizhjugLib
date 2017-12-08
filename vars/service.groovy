import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.KubernetesResource
import com.zenika.tz.demo.deploy.Service
import com.zenika.tz.demo.deploy.ServiceType

KubernetesResource call(Map params = null, Closure body) {

    ServiceType serviceType
    if(!params || !params.type) {
        serviceType = ServiceType.ClusterIP
    } else {
        serviceType = ServiceType.valueOf(params.type)
    }

    Service service = new Service()
    service.type = serviceType
    try {
        body()
        service.configure()
        PipelineContextHolder.kubernetes.addResource(service)
        trace(service.toString())
    } catch (err) {
        error(err.getMessage())
    }

    return service
}