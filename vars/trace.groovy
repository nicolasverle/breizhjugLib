import com.zenika.tz.demo.PipelineContextHolder

def call(String message) {
    if(PipelineContextHolder.verbose) {
        echo(message)
    }
}