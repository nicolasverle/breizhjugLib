import com.zenika.tz.demo.test.QualityRequirements
import groovy.transform.Field

import static com.zenika.tz.demo.PipelineContextHolder.buildContext

@Field QualityRequirements requirements

def call(Closure body) {

    node(buildContext.nodes) {
        requirements = QualityRequirements.builder().withDefaultRequirements()
        if(body) {
            body.resolveStrategy = Closure.DELEGATE_FIRST
            body.delegate = this
            body()
        }
        buildContext.strategy().test()
    }

}

def withRequirements(Map params) {
    if(params.maxCriticals) {
        requirements.maxCriticals = params.maxCriticals
    }

}