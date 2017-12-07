package com.zenika.tz.demo.deploy

interface KubernetesResource {

    def configure()

    void deploy()

    void rollback()

    String name()

    String manifest()

}