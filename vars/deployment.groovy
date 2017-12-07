import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.Deployment
import groovy.transform.Field

@Field Deployment deploy

def call(Map params, Closure body) {

    if(body) {
        deploy = new Deployment(params.replicas)

        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()

        deploy.configure()

        PipelineContextHolder.kubernetes.addResource(deploy)
    }

}

void rollingUpdate(Map params) {
    deploy.strategy = [
        'strategy': [
            'type': 'RollingUpdate',
            'rollingUpdate': [
                'maxUnavailable': params.maxUnavailable,
                'maxSurge': params.maxSurge
            ]
        ]
    ]
}