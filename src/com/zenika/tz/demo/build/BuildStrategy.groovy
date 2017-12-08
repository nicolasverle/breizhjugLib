package com.zenika.tz.demo.build

interface BuildStrategy {

    void build()

    void createImage(String dockerfile, String tag)

    String getNodesLabel()

    SourcesTypeEnum getProjectType()

}