import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Deployment
import com.zenika.tz.demo.deploy.Pod
import groovy.transform.Field

@Field Deployment deploy

def call(Map params, Closure body) {

    try {
        if(body) {
            deploy = new Deployment(params.replicas)

            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            deploy.setPod((Pod)body())

            deploy.configure()
            PipelineContextHolder.kubernetes.deploymentWraping = true
            PipelineContextHolder.kubernetes.addResource(deploy)

            trace(deploy.toString())
        }
    } catch (err) {
        error(err.getMessage())
    }

}

void rollingUpdate(Map params) {
    deploy.strategy = [
        'type': 'RollingUpdate',
        'rollingUpdate': [
            'maxUnavailable': params.maxUnavailable,
            'maxSurge': params.maxSurge
        ]
    ]
}