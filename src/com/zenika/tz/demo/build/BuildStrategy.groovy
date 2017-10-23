package com.zenika.tz.demo.build

interface BuildStrategy {

    void build()

    def test()

    def dockerize()

}