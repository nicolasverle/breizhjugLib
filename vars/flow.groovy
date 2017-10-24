import com.zenika.tz.demo.PipelineContextHolder

def call(Map params = null, Closure body) {

    PipelineContextHolder.script = this
    body()
}