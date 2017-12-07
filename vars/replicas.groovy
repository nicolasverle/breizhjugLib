import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Deployment
import com.zenika.tz.demo.deploy.KubernetesResource
import com.zenika.tz.demo.deploy.Pod
import com.zenika.tz.demo.deploy.Deployment

KubernetesResource call(int number, Closure body) {

    Deployment replicaSet = new Deployment(number)
    replicaSet.pod = (Pod)body()
    replicaSet.configure()

    PipelineContextHolder.kubernetes.addResource(replicaSet)

    return replicaSet
}