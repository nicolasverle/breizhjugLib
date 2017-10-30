package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

final class DockerHandler extends CommandWrapper {

    void deploy(String host, String image = null, String tag = null, List<Map<Integer, Integer>> ports = null, List<Map<String, String>> volumes = null, String opts = "") {

        if(!image) {
            image = appName()
        }
        if(!tag) {
            image = appVersion()
        }

        if(getRegistry()) {
            image = getRegistry() + "/" + image
        }

        if(host != "localhost") {
            docker().withServer("tcp://${host}:2376", host) {
                sh("docker run --name ${appVersion()} -d ${ports?.collect{ "-p " + it.host + ":" + it.container }.join(" ")} ${volumes?.collect{ "-v " + it.host + ":" + it.container }.join(" ")} ${opts} ${image}:${tag}")
            }
        }
    }

}
