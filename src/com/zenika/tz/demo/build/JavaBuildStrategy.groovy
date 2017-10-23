package com.zenika.tz.demo.build

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.ResultFormatEnum
import com.zenika.tz.demo.test.QualityRequirements

class JavaBuildStrategy extends CommandWrapper implements BuildStrategy {

    private static final String MAVEN_ALIAS = "m3"

    @Override
    void build() {
        withMaven(MAVEN_ALIAS) {
            sh("mvn package")
        }
    }

    @Override
    def test(QualityRequirements requirements = null) {
        withMaven(MAVEN_ALIAS) {
            sh("mvn test")
            junit("target/surefire-reports/*.xml")
            if(requirements) {
                if(requirements.minCoverage) {
                    sh("mvn cobertura:cobertura")
                    def report = parseFile("cobertura.xml", ResultFormatEnum.XML)
                    report[line]
                }
            }
        }
        return null
    }

    @Override
    def dockerize() {
        return null
    }
}
