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
    void createImage(String dockerfile, String tag = null) {
        String registry = (getRegistry() ? getRegistry() + "/": "")
        if(!tag) {
            tag = registry + appName()
        }

        sh("docker build -f ${dockerfile} -t ${tag} .")
        sh("docker push ${tag}")
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
