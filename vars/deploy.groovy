import com.zenika.tz.demo.deploy.DockerHandler
import groovy.transform.Field

@Field String host
@Field int port

def call(Map params, Closure body) {

    if(!params.host) {
        error("Host must be set when deploying")
    }
    if(!params.port) {
        error("Port must be set when deploying")
    }

    host = params.host
    port = params.port

    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()
    }

}

def dockerd(Map params) {
    def ports = [[host: port, container: 8080]]
    if(params.ports) {
        ports.addAll(params.ports)
    }

    new DockerHandler().deploy(host, params.image, params.tag,
            ports, params.volumes, params.opts)
}