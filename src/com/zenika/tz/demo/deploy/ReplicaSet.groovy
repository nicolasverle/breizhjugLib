package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class ReplicaSet extends CommandWrapper implements KubernetesResource {

    int replicas

    KubernetesResource pod

    ReplicaSet(int number) {
        replicas = number
    }

    def configure() {
        def rs = [
            "apiVersion": "apps/v1beta2",
            "kind": "ReplicaSet",
            "metadata": [
                "name": pod.name(),
            ],
            "spec": [
                "replicas": replicas,
                "selector": [
                    "matchLabels": [
                        "app": pod.name()
                    ]
                ]
            ]
        ]

        writeYaml(manifest(), rs)

        return rs
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
        return pod.name()
    }

    String manifest() {
        return "replicaSet.yaml"
    }
}
