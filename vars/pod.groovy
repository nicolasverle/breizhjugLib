import com.zenika.tz.demo.deploy.Container
import com.zenika.tz.demo.deploy.Pod

def call(Map params, Closure body) {

    Pod pod = new Pod()
    pod.setName(params.name)
    pod.setImagePullSecrets(params.imagePullSecrets)

    List<Container> containers = []
    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        containers.addAll(body())
    }

    pod.setContainers(containers)

    pod.configure()
}

Container container(Map params) {
    Container container = new Container()
    container.setName(params.name)
    container.setImage(params.image)
    container.setImagePullPolicy(params.imagePullPolicy)
    container.setContainerPort(params.containerPort)
}