import com.zenika.tz.demo.build.JavaBuildStrategy

import static com.zenika.tz.demo.PipelineContextHolder.buildStrategy

def call(Closure body) {
    if(body) {
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = this
        body()
    }
}

def java() {
    buildStrategy = new JavaBuildStrategy()
    node(buildStrategy.getNodesLabel()) {
        checkout scm
        buildStrategy.build()
    }
}