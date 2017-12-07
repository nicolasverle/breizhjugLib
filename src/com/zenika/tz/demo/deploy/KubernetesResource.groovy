package com.zenika.tz.demo.deploy

interface KubernetesResource extends Serializable {

    def configure()

    void deploy()

    void rollback()

}