package com.zenika.tz.demo.test

import com.zenika.tz.demo.CommandWrapper
import com.zenika.tz.demo.ResultFormatEnum

class QualityChecker extends CommandWrapper {

    void ensureCoverageRateAttained(double minimum) {
        def report = parseFile("cobertura-coverage.xml", ResultFormatEnum.XML)
        def coverage = report.@'line-rate'
        if(coverage < minimum) {
            error("${coverage} is inferior at coverage min value defined as ${requirements.minCoverage}")
        }
    }

}
