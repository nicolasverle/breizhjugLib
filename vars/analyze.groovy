import com.zenika.tz.demo.test.QualityRequirements
import groovy.transform.Field

import static com.zenika.tz.demo.PipelineContextHolder.buildContext

@Field QualityRequirements requirements

def call(Closure body) {

    node(buildContext.nodes) {
        if(body) {
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            body()
        }
        buildContext.strategy().analyze(requirements)
    }

}

def failIf(Map params) {
    if(params) {
        requirements =
            QualityRequirements.builder()
                .withMinCoverage(params.minCoverage)
                .withMaxBlockings(params.maxBlocking)
                .withMaxCriticals(params.maxCriticals)
            .build()
    }
}