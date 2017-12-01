package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Pod extends CommandWrapper implements KubernetesResource {

    String name

    List<Container> containers

    List imagePullSecrets

    void configure() {
        def pod = [
                'api': 'v1',
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
                     'containerPort': container.getContainerPort()
                 ]
            ])
        }

        if(imagePullSecrets) {
            pod.spec.imagePullSecrets = imagePullSecrets
        }

        writeYaml("pod.yaml", pod)
    }

    void deploy() {
        sh("kubectl apply -f pod.yaml")
    }
}
