package com.zenika.tz.demo

import com.zenika.tz.demo.build.BuildStrategy
import com.zenika.tz.demo.deploy.Kubernetes

final class PipelineContextHolder {

    static script

    static BuildStrategy buildStrategy

    static String dockerRegistry

    static boolean verbose

    static Kubernetes kubernetes

    static void init(pipelineContext) {
        script = pipelineContext
    }

}
