package com.zenika.tz.demo.build

enum BuildContextEnum {

    JAVA("master") {
        BuildStrategy strategy() {
            return new JavaBuildStrategy()
        }
    },
    PHP("phpNodes") {
        BuildStrategy strategy() {
            return null
        }
    },
    JS("jsNodes") {
        BuildStrategy strategy() {
            return null
        }
    },
    PYTHON("pythonNodes") {
        BuildStrategy strategy() {
            return null
        }
    }

    String nodes

    BuildContextEnum(String nodes) {
        this.nodes = nodes
    }

    abstract BuildStrategy strategy();

}