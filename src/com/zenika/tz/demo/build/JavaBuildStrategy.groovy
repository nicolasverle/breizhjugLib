package com.zenika.tz.demo.build

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.ResultFormatEnum
import com.zenika.tz.demo.test.QualityRequirements

class JavaBuildStrategy extends CommandWrapper implements BuildStrategy {

    private static final String MAVEN_ALIAS = "m3"

    private static final String SONAR_URL = "http://192.168.33.62:9000"

    @Override
    void build() {
        withMaven(MAVEN_ALIAS) {
            sh("mvn package")
            junit("target/surefire-reports/*.xml")
        }
    }

    @Override
    void analyze(QualityRequirements requirements = null) {
        withMaven(MAVEN_ALIAS) {
            sh("mvn sonar:sonar -Dsonar.analysis.mode=preview -Dsonar.report.export.path=report.json -Dsonar.issuesReport.console.enable=true -Dsonar.host.url=${SONAR_URL}")
            if(requirements) {
                requirements.eval(parseFile("target/sonar/report.json", ResultFormatEnum.JSON))
            }
        }
    }

    @Override
    void createImage(String dockerfile) {
        sh("docker buid - < ${dockerfile}")
    }

    @Override
    String getNodesLabel() {
        return "master"
    }
}
