import com.zenika.tz.demo.PipelineContextHolder
import com.zenika.tz.demo.test.QualityRequirements
import groovy.transform.Field

@Field QualityRequirements requirements

def call(Closure body) {

    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        if(body) {
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            body()
        }
        PipelineContextHolder.buildStrategy.analyze(requirements)
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