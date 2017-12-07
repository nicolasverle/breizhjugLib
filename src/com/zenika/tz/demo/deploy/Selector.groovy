package com.zenika.tz.demo.deploy

class Selector implements Serializable {

    def app(String name) {
        return [
            'selector': [
                'app': name
            ]
        ]
    }

    def matchLabel(String key, String value) {
        return [
            'selector': [
                'matchLabels'
            ]
        ]
    }

}
