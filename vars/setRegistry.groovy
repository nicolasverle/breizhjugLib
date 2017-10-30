import com.zenika.tz.demo.PipelineContextHolder

def call(String address) {
    PipelineContextHolder.dockerRegistry = address
}