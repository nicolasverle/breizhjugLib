import com.zenika.tz.demo.PipelineContextHolder

def call(Map params = null, Closure body) {

    setContext()

    if(params) {
        if(params.verbose == true) {
            PipelineContextHolder.verbose = true
        }
    }

    try {
        body()
    } catch (err) {
        error(err.getMessage())
    }

}