import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Container
import com.zenika.tz.demo.deploy.KubernetesResource
import com.zenika.tz.demo.deploy.Pod

Pod call(Map params, Closure body) {

    Pod pod = new Pod()
    pod.setImagePullSecrets(params.imagePullSecrets)

    try {
        List<Container> containers = []
        if(body) {
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            containers.addAll(body())
        }

        pod.setContainers(containers)
        pod.configure()
    } catch (err) {
        error(err.getMessage())
    }

    PipelineContextHolder.kubernetes.addResource(pod)

    return pod
}

Container container(Map params) {
    Container container = new Container()
    container.setName(PipelineContextHolder.deployContext.appName)
    container.setImage(params.image)
    container.setImagePullPolicy(params.imagePullPolicy)
    container.setContainerPort(PipelineContextHolder.deployContext.appPort)
    return container
}