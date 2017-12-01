package com.zenika.tz.demo.deploy

import com.zenika.tz.demo.CommandWrapper

class Kubernetes extends CommandWrapper {

    String namespace


    Kubernetes(String namespace = "default") {
        this.namespace = namespace
    }



}
