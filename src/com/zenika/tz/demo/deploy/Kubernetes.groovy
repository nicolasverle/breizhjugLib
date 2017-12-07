package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Kubernetes extends CommandWrapper {

    String namespace

    List<KubernetesResource> manifests

    def config

    Kubernetes(String namespace = "default") {
        echo("Kubernetes constructor...")
        this.namespace = namespace
        initContext()
    }

    private void initContext() {
        config = yaml {
            cmd("kubectl config current-context")
        }
        if(config.namespace != this.namespace) {
            sh("kubectl config set-context \$(kubectl config current-context) --namespace=${this.namespace}")
        }
    }

    void apply() {
        for(KubernetesResource resource : manifests) {
            resource.deploy()
        }
    }

    void addResource(KubernetesResource resource) {
        if(!manifests) {
            manifests = new LinkedList<>()
        }
        manifests.add(resource)
    }


}
