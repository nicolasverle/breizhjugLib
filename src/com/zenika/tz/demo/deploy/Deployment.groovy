package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.PipelineContextHolder

class Deployment extends CommandWrapper implements KubernetesResource {

    String name

    int replicas

    KubernetesResource pod

    def strategy

    Deployment(int number) {
        replicas = number
        name = PipelineContextHolder.deployContext.appName
    }

    def configure() {
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
        return "deployment.yaml"
    }
}
