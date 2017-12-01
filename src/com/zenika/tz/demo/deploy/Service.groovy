package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Service extends CommandWrapper implements KubernetesResource {

    String name

    ServiceType type

    void deploy() {

    }
}
