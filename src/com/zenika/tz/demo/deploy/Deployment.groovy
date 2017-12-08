package com.zenika.tz.demo.deploy

class Deployment extends AbstractKubernetesResource {

    static final String MANIFEST_FILE = "deployment.yaml"

    int replicas

    def pod

    def strategy

    Deployment(int number) {
        replicas = number
    }

    def configure() {
        String name = appName()

        def deployment = [
            "apiVersion": "apps/v1beta2",
            "kind": "Deployment",
            "metadata": [
                "name": name,
            ],
            "spec": [
                "replicas": replicas,
                "selector": [
                    "matchLabels": [
                        "app": name
                    ]
                ],
                "template": [
                    "metadata": pod.metadata,
                    "spec": pod.spec
                ]
            ]
        ]

        if(strategy) {
            deployment.spec.strategy = strategy
        }

        writeYaml(manifest(), deployment)

        return deployment
    }

    void setPod(Pod pod) {
        this.pod = yaml {
            pod.toString()
        }
    }

    String manifest() {
        return MANIFEST_FILE
    }
}
