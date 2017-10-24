package com.zenika.tz.demo.build

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.ResultFormatEnum
import com.zenika.tz.demo.test.QualityChecker
import com.zenika.tz.demo.test.QualityRequirements
import groovy.json.JsonOutput

class JavaBuildStrategy extends CommandWrapper implements BuildStrategy {

    private static final String MAVEN_ALIAS = "m3"

    private static final String SONAR_URL = "http://192.168.33.62:9000"

    private QualityChecker qualityChecker

    @Override
    void build() {
        withMaven(MAVEN_ALIAS) {
            sh("mvn package")
            junit("target/surefire-reports/*.xml")
        }
    }

    @Override
    def analyze(QualityRequirements requirements = null) {
        withMaven(MAVEN_ALIAS) {
            sh("mvn sonar:sonar -Dsonar.report.export.path=report.json -Dsonar.issuesReport.console.enable=true -Dsonar.host.url=${SONAR_URL}")
            if(requirements) {
                def json = parseFile("report.json", ResultFormatEnum.JSON)
                echo("Will parse : ${JsonOutput.prettyPrint(json.toString())}")
            }
        }
        return null
    }

    @Override
    def dockerize() {
        return null
    }
}
