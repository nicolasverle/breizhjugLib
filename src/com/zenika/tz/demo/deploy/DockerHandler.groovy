package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class DockerHandler extends CommandWrapper {

    String host = "localhost"

    DockerHandler(String host = null) {
        if(host) {
            this.host = host
        }
    }

    void deploy(String image = null, String tag = null, List<Map<Integer, Integer>> ports = null, List<Map<String, String>> volumes = null, String opts = "") {

        if(!image) {
            image = appName()
        }
        if(!tag) {
            tag = appVersion()
        }

        if(getRegistry()) {
            image = getRegistry() + "/" + image
        }

        if(host != "localhost") {
            docker().withServer("tcp://${host}:2376", host) {
                if(cmd("docker ps -aq -f name=${appName()}")) {
                    sh("docker rm -f ${appName()}")
                }
                sh("docker pull ${image}:${tag}")
                sh("docker deploying --name ${appName()} -d ${ports?.collect{ "-p " + it.host + ":" + it.container }?.join(" ")} ${volumes?.collect{ "-v " + it.host + ":" + it.container }?.join(" ")} ${opts} ${image}:${tag}")
            }
        }
    }

}
