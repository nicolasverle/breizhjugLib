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
        for(KubernetesResource resource : manifests) {
            if(resource.manifest() == Pod.MANIFEST_FILE && deploymentWraping) continue

            if(manualValidation) {
                resource.deploy(true)
                if(confirm()) {
                    resource.deploy(false)
                } else {
                    error("Build aborted by user.")
                }
            }

        }
    }

    void addResource(KubernetesResource resource) {
        if(!manifests) {
            manifests = new LinkedList<>()
        }
        manifests.add(resource)
    }

}
