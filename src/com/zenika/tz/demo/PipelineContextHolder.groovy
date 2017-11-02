package com.zenika.tz.demo

import com.zenika.tz.demo.build.BuildStrategy

final class PipelineContextHolder {

    static script

    static BuildStrategy buildStrategy

    static String dockerRegistry

    static boolean verbose

}
