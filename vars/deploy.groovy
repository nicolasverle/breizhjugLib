import com.zenika.tz.demo.PipelineContextHolder
import groovy.transform.Field

def call(Map params, Closure body) {

    PipelineContextHolder.deployContext.appName = params.appName
    PipelineContextHolder.deployContext.port = params.appPort

    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        stage("Deploying application") {
            if(body) {
                body.resolveStrategy = Closure.DELEGATE_FIRST
                body.delegate = this
                body()
            }
        }
    }


}