package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.PipelineContextHolder

class Ingress extends CommandWrapper implements KubernetesResource {

    String host

    Ingress(String host) {
        this.host = host
    }

    def configure() {
        def service = yaml(Service.manifest())

        def ingress = [
            'apiVersion': 'extensions/v1beta1',
            'kind': 'Ingress',
            'metadata': [
                'name': service.metadata.name,
                'annotations': [
                    'kubernetes.io/ingress.class': 'traefik'
                ]
            ],
            'spec': [
                'rules': [[
                    'host': host,
                    'http': [
                        'paths': [[
                            'backend': [
                              'serviceName': PipelineContextHolder.deployContext.appName,
                              'servicePort': PipelineContextHolder.deployContext.appPort
                            ]
                        ]]
                    ]
                ]]
            ]
        ]

        writeYaml(manifest(), ingress)

        return ingress
    }

    void deploy() {
        sh("kubectl apply -f ${manifest()}")
    }

    void rollback() {

    }


    static String manifest() {
        return "ingress.yaml"
    }

}
