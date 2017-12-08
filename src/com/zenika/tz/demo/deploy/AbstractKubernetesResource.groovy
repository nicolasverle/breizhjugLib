package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.PipelineContextHolder

abstract class AbstractKubernetesResource extends CommandWrapper implements KubernetesResource {

    void deploy(boolean dryRun) {
        sh("kubectl apply ${(dryRun ? "--dry-run=true" : "")} -f ${manifest()}")
    }

    void rollback() {
        sh("kubectl delete -f ${manifest()}")
    }

    String appName() {
        return PipelineContextHolder.deployContext.appName
    }

    int appPort() {
        return PipelineContextHolder.deployContext.appPort
    }

    abstract String manifest()

    abstract def configure()

    @Override
    String toString() {
        return sh("pygmentize -O style=fruity ${manifest()}")
    }

}
