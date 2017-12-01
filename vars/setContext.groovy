import com.zenika.tz.demo.PipelineContextHolder

def call() {
    if(!PipelineContextHolder.script) {
        PipelineContextHolder.init(this)
    }
}