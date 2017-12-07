package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.PipelineContextHolder

class Pod extends CommandWrapper implements KubernetesResource {

    String name

    List<Container> containers

    List imagePullSecrets

    Pod() {
        name = PipelineContextHolder.deployContext.appName
    }

    def configure() {
        def pod = [
                'apiVersion': 'v1',
                'kind': 'Pod',
                'metadata': [
                    'name': name,
                    'labels': [
                        'app': name
                    ]
                ],
                'spec': [
                    'containers': []
                ]
        ]

        for(Container container : containers) {
            pod.spec.containers.add([
                 'name': container.getName(),
                 'image': container.getImage(),
                 'ports': [
                     [ 'containerPort': container.getContainerPort() ]
                 ]
            ])
        }

        if(imagePullSecrets) {
            def secrets = []
            for(String secret : imagePullSecrets) {
                secrets.add([ 'name': secret ])
            }
            pod.spec.imagePullSecrets = secrets
        }

        writeYaml(manifest(), pod)

        return pod
    }

    void deploy() {
        sh("kubectl apply -f ${manifest()}")
    }

    void rollback() {
        sh("kubectl delete -f ${manifest()}")
    }

    String name() {
        return name
    }

    static String manifest() {
        return "pod.yaml"
    }
}
