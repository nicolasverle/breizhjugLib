package com.zenika.tz.demo.deploy

interface KubernetesResource {

    void configure()

    void deploy()

}