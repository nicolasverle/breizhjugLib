package com.zenika.tz.demo.deploy

class Pod extends AbstractKubernetesResource {

    List<Container> containers

    List imagePullSecrets

    def configure() {
        String name = appName()

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

    static String manifest() {
        return "pod.yaml"
    }
}
