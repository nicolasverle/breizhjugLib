package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Service extends CommandWrapper implements KubernetesResource {

    String name

    ServiceType type

    int port

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
                    'app': deployment.metadata.name
                ],
                'ports': [[
                    'protocol': "TCP",
                    'port': port
                ]],
                'type': type.toString()
            ]
        ]
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

    String name() {
        return name
    }

    static String manifest() {
        return "service.yaml"
    }
}
