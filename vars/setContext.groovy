import com.zenika.tz.demo.PipelineContextHolder

def call() {
    echo("setting context...")
    if(!PipelineContextHolder.script) {
        PipelineContextHolder.init(this)
    }
}