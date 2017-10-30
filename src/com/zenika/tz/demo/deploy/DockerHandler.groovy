package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

final class DockerHandler extends CommandWrapper {

    void deploy(String host, String image = appName(), String tag = appVersion(),
                       List<Map<Integer, Integer>> ports = null, List<Map<String, String>> volumes = null, String opts = null) {
        if(host != "localhost") {
            docker().withServer("tcp://${host}:2376", host) {
                sh("docker run --name ${appVersion()} -d ${ports?.collect{ "-p " + it.host + ":" + it.container }.join(" ")} ${volumes?.collect{ "-v " + it.host + ":" + it.container }.join(" ")} ${opts} ${image}:${tag}")
            }
        }
    }

}
