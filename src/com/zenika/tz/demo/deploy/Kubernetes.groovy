package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Kubernetes extends CommandWrapper {

    String namespace

    List<KubernetesResource> manifests

    boolean deploymentWraping

    boolean manualValidation

    def config

    Kubernetes(String ns) {
        namespace = ns
    }

    void initContext() {
        config = yaml {
            cmd("kubectl config view")
        }
        if(config.namespace != namespace) {
            sh("kubectl config set-context \$(kubectl config current-context) --namespace=${namespace}")
        }
    }

    void apply() {
        for (int i = 0; i < manifests.size(); i++) {
            if(manifests.get(i).manifest() == Pod.MANIFEST_FILE && deploymentWraping) continue
            echo(manifests.get(i).toString())
            manifests.get(i).deploy(manualValidation)
        }

        if(manualValidation) {
            askForConfirmation()
            manualValidation = false
            apply()
        }
    }

    void addResource(KubernetesResource resource) {
        if(!manifests) {
            manifests = new LinkedList<>()
        }
        manifests.add(resource)
    }

}
