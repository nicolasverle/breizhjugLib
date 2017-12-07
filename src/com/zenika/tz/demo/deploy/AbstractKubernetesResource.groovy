package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

abstract class AbstractKubernetesResource extends CommandWrapper {

    void deploy() {
        sh("kubectl apply -f ${manifest()}")
    }

    void rollback() {
        sh("kubectl delete -f ${manifest()}")
    }

    abstract String manifest()

    abstract def configure()

}
