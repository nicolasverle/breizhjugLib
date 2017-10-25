package com.zenika.tz.demo.build

import com.zenika.tz.demo.test.QualityRequirements

interface BuildStrategy {

    void build()

    void analyze(QualityRequirements requirements)

    void createImage(String dockerfile)

    String getNodesLabel()

}