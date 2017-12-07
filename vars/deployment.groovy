import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Deployment
import groovy.transform.Field

@Field Deployment deployment

def call(Map params, Closure body) {

    if(body) {
        deployment = new Deployment(params.replicas)

        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()

        deployment.configure()

        PipelineContextHolder.kubernetes.addResource(deployment)
    }

}

void rollingUpdate(Map params) {
    deployment.strategy = [
        'strategy': [
            'type': 'RollingUpdate',
            'rollingUpdate': [
                'maxUnavailable': params.maxUnavailable,
                'maxSurge': params.maxSurge
            ]
        ]
    ]
}