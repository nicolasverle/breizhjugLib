package com.zenika.tz.demo.build

enum BuildContextEnum {

    JAVA("master"),
    PHP("phpNodes"),
    JS("jsNodes"),
    PYTHON("pythonNodes")

    private String nodes

    BuildContextEnum(String nodes) {
        this.nodes = nodes
    }

    public String getNodes() {
        return this.nodes
    }

}