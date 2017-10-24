package com.zenika.tz.demo.test

final class QualityRequirements {

    Integer maxCriticals

    Integer maxBlockings

    Double minCoverage

    private QualityRequirements() {}

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
