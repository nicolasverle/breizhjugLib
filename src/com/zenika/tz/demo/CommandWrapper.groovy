package com.zenika.tz.demo

import com.cloudbees.groovy.cps.NonCPS
import com.zenika.tz.demo.build.SourcesTypeEnum

class CommandWrapper implements Serializable {

    def script

    def formatter = new CommandResultFormatter()

    CommandWrapper() {
        echo("command wrapper constructor")
        if(!PipelineContextHolder.script) {
            throw new ExceptionInInitializerError("No context found for this build.")
        }
        script = PipelineContextHolder.script
    }

    void echo(String message) {
        script.echo(message)
    }

    void error(String alert) {
        script.error(alert)
    }

    def withMaven(String version, Closure body) {
        script.withMaven(maven: version) {
            body()
        }
    }

    void sh(String cmd) {
        script.sh(cmd)
    }

    void junit(String path) {
        script.junit(path)
    }

    String appVersion() {
        switch (PipelineContextHolder.buildStrategy.getProjectType()) {
            case SourcesTypeEnum.JAVA :
                return script.readMavenPom()?.version
                break
            default:
                return null
        }
    }

    String appName() {
        switch (PipelineContextHolder.buildStrategy.getProjectType()) {
            case SourcesTypeEnum.JAVA :
                return script.readMavenPom()?.artifactId
                break
            default:
                return null
        }
    }

    String getRegistry() {
        return PipelineContextHolder.dockerRegistry
    }

    boolean isDebug() {
        return PipelineContextHolder.verbose
    }

    def yaml(Closure body) {
        String content = body()
        return script.readYaml(text: content)
    }

    def cmd(String command, rtnFormat = ResultFormatEnum.TEXT) {
        String text = script.sh(returnStdout: true, script: command)
        switch (rtnFormat) {
            case ResultFormatEnum.JSON:
                return formatter.toJSON(text)
                break
            case ResultFormatEnum.XML:
                return formatter.toXML(text)
                break
            case ResultFormatEnum.YAML:
                return formatter.toYAML(text)
                break
            default:
                return text
        }
    }



    def parseFile(String name, ResultFormatEnum format = ResultFormatEnum.TEXT) {
        switch (format) {
            case ResultFormatEnum.JSON:
                return script.readJSON(file: name)
                break
            case ResultFormatEnum.XML:
                return formatter.toXML(script.readFile(file: name))
                break
            case ResultFormatEnum.YAML:
                return script.readYaml(file: name)
                break
            default:
                return script.readFile(file: name)
        }
    }

    def docker() {
        return script.docker
    }

    private static class CommandResultFormatter {
        @NonCPS
        def toXML(String body) {
            return new XmlSlurper().parseText(body)
        }
    }

}
