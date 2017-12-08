package com.zenika.tz.demo.deploy

class Service extends AbstractKubernetesResource {

    static final String MANIFEST_FILE = "service.yaml"

    ServiceType type

    int targetPort

    def configure() {
        String name = appName()

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
                    'port': appPort()
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

    String manifest() {
        return MANIFEST_FILE
    }
}
