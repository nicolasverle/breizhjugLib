import com.zenika.tz.demo.PipelineContextHolder

def trace(String message) {
    if(PipelineContextHolder.verbose) {
        echo(message)
    }
}