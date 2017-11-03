import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.deploy.DockerHandler
import groovy.transform.Field

@Field String host

def call(Map params, Closure body) {

    if(!params.host) {
        error("Host must be set when deploying")
    }

    host = params.host

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

def dockerd(Map params) {
    String opts = "-m 512m"
    if(params.opts) {
        opts += params.opts
    }

    DockerHandler handler = new DockerHandler(host)

    handler.deploy(params.image, params.tag,
            params.ports, params.volumes, opts)
}