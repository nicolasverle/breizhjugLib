package com.zenika.tz.demo.build

interface BuildStrategy {

    void build()

    void createImage(String dockerfile)

    String getNodesLabel()

    SourcesTypeEnum getProjectType()

}