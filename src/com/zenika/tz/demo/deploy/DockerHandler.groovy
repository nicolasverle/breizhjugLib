package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

final class DockerHandler extends CommandWrapper {

    static void deploy(String host, List<Map<Integer, Integer>> ports, List<Map<String, String>> volumes, String opts = null) {
        if(host != "localhost") {
            docker().withServer("tcp://${host}:2376", host) {
                sh("docker run --name ${appVersion()} -d ${ports.join("-p ")}")
            }
        }
    }

}
