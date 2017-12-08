package com.zenika.tz.demo.build

import com.zenika.tz.demo.CommandWrapper

class JavaBuildStrategy extends CommandWrapper implements BuildStrategy {

    private static final String MAVEN_ALIAS = "m3"

    @Override
    void build() {
        withMaven(MAVEN_ALIAS) {
            def cmd = "mvn package"
            if(isDebug()) {
                cmd += " -X"
            }
            sh(cmd)
            junit("target/surefire-reports/*.xml")
        }
    }

    @Override
    void createImage(String dockerfile) {
        String registry = (getRegistry() ? getRegistry() + "/": "")

        sh("docker build -f ${dockerfile} -t ${appName()}:${appVersion()} .")
        sh("docker tag ${appName()}:${appVersion()} ${registry}${appName()}:${appVersion()}")
        sh("docker push ${registry}${appName()}:${appVersion()}")
    }

    @Override
    String getNodesLabel() {
        return "master"
    }

    @Override
    SourcesTypeEnum getProjectType() {
        return SourcesTypeEnum.JAVA
    }
}
