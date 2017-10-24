package com.zenika.tz.demo.build

import com.zenika.tz.demo.test.QualityRequirements

interface BuildStrategy {

    void build()

    def analyze(QualityRequirements requirements)

    def dockerize()

}