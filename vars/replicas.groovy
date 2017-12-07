import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.KubernetesResource
import com.zenika.tz.demo.deploy.Pod
import com.zenika.tz.demo.deploy.ReplicaSet

KubernetesResource call(int number, Closure body) {

    ReplicaSet replicaSet = new ReplicaSet(number)
    replicaSet.pod = (Pod)body()
    replicaSet.configure()

    PipelineContextHolder.kubernetes.addResource(replicaSet)

    return replicaSet
}