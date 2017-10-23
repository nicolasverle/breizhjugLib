package com.zenika.tz.demo.test

final class QualityRequirements {

    int maxCriticals

    int maxBlockings

    double minCoverage

    private QualityRequirements() {}

    static QualityRequirementsBuilder builder() {
        return new QualityRequirementsBuilder(new QualityRequirements())
    }

    private static class QualityRequirementsBuilder {

        QualityRequirements qualityRequirements

        QualityRequirementsBuilder(QualityRequirements qualityRequirements) {
            this.qualityRequirements = qualityRequirements
        }

        QualityRequirementsBuilder withMaxCriticals(int maxCriticals) {
            qualityRequirements.maxCriticals = maxCriticals
            return this
        }

        QualityRequirementsBuilder withMaxBlockings(int maxBlockings) {
            qualityRequirements.maxBlockings = maxBlockings
            return this
        }

        QualityRequirementsBuilder withMinCoverage(double minCoverage) {
            qualityRequirements.minCoverage = minCoverage
            return this
        }

        QualityRequirements withDefaultRequirements() {
            qualityRequirements.maxBlockings = 1
            qualityRequirements.maxCriticals = 0
            qualityRequirements.minCoverage = 80.0
            return qualityRequirements
        }

        QualityRequirements build() {
            return qualityRequirements
        }

    }

}
