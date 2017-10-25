package com.zenika.tz.demo.test

import com.zenika.tz.demo.CommandWrapper

final class QualityRequirements extends CommandWrapper {

    Integer maxCriticals

    Integer maxBlockings

    Double minCoverage

    private QualityRequirements() {}

    void eval(report) {
        if(maxCriticals) {
            int criticals = report.issues.findAll { it.severity == "CRITICAL" }?.size()
            if(criticals > maxCriticals) {
                error("Got ${criticals} critical issue(s), maximum was set to ${maxCriticals}")
            }
        }
        if(maxBlockings) {
            int blockings = report.issues.findAll { it.severity == "CRITICAL" }?.size()
            if(blockings > maxBlockings) {
                error("Got ${blockings} blocking issue(s), maximum was set to ${maxBlockings}")
            }
        }
    }

    static QualityRequirementsBuilder builder() {
        return new QualityRequirementsBuilder(new QualityRequirements())
    }

    private static class QualityRequirementsBuilder {

        QualityRequirements qualityRequirements

        QualityRequirementsBuilder(QualityRequirements qualityRequirements) {
            this.qualityRequirements = qualityRequirements
        }

        QualityRequirementsBuilder withMaxCriticals(Object maxCriticals) {
            if(maxCriticals) {
                qualityRequirements.maxCriticals = (Integer)maxCriticals
            }
            return this
        }

        QualityRequirementsBuilder withMaxBlockings(Object maxBlockings) {
            if(maxBlockings) {
                qualityRequirements.maxBlockings = (Integer)maxBlockings
            }
            return this
        }

        QualityRequirementsBuilder withMinCoverage(Object minCoverage) {
            if(minCoverage) {
                qualityRequirements.minCoverage = (Double)minCoverage
            }
            return this
        }

        QualityRequirements build() {
            return qualityRequirements
        }

    }

}
