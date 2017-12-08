package com.zenika.tz.demo.deploy

class Deployment extends AbstractKubernetesResource {

    int replicas

    KubernetesResource pod

    def strategy

    Deployment(int number) {
        replicas = number
    }

    def configure() {
        String name = appName()
        def pod = yaml(Pod.manifest())

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

    static String manifest() {
        return "deployment.yaml"
    }
}
