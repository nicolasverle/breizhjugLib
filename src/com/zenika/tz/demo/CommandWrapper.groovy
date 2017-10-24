package com.zenika.tz.demo

import com.cloudbees.groovy.cps.NonCPS

class CommandWrapper implements Serializable {

    def script

    def formatter = new CommandResultFormatter()

    CommandWrapper() {
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
                return script.readJSON(name)
                break
            case ResultFormatEnum.XML:
                return formatter.toXML(script.readFile(name))
                break
            case ResultFormatEnum.YAML:
                return script.readYaml(name)
                break
            default:
                return script.readFile(name)
        }
    }

    private static class CommandResultFormatter {

        @NonCPS
        def toXML(String body) {
            return new XmlSlurper().parseText(body)
        }

    }

}
