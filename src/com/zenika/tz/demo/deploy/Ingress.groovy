package com.zenika.tz.demo.deploy

class Ingress extends AbstractKubernetesResource {

    static final String MANIFEST_FILE = "ingress.yaml"

    String host

    Ingress(String host) {
        this.host = host
    }

    def configure() {
        String name = appName()

        def ingress = [
            'apiVersion': 'extensions/v1beta1',
            'kind': 'Ingress',
            'metadata': [
                'name': name,
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
                              'serviceName': name,
                              'servicePort': appPort()
                            ]
                        ]]
                    ]
                ]]
            ]
        ]

        writeYaml(manifest(), ingress)

        return ingress
    }

    String manifest() {
        return MANIFEST_FILE
    }

}
