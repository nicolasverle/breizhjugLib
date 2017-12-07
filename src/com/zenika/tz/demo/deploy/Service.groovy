package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.PipelineContextHolder

class Service extends CommandWrapper implements KubernetesResource {

    String name

    ServiceType type

    int port

    int targetPort

    Service() {
        name = PipelineContextHolder.deployContext.appName
        port = PipelineContextHolder.deployContext.appPort
    }

    def configure() {
        def deployment = yaml("deployment.yaml")

        def service = [
            'apiVersion': 'v1',
            'kind': 'Service',
            'metadata': [
                'name': name,
            ],
            'spec': [
                'selector': [
                    'app': name
                ],
                'ports': [[
                    'protocol': "TCP",
                    'port': port
                ]],
                'type': type.toString()
            ]
        ]

        if(targetPort > 0) {
            service.spec.ports[0].targetPort = targetPort
        }

        writeYaml(manifest(), service)
        return service
    }

    void deploy() {
        try {
            sh("kubectl apply -f ${manifest()}")
        } catch (err) {
            error(err.getMessage())
        }

    }

    void rollback() {
        sh("kubectl delete -f ${manifest()}")
    }

    static String manifest() {
        return "service.yaml"
    }
}
