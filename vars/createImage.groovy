import com.zenika.tz.demo.PipelineContextHolder

def call(Map params) {

    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        String dockerfile = "Dockerfile"
        if(params.script) {
            writeFile(file: dockerfile, text: params.script.trim())
        } else if(params.file) {
            dockerfile = params.file
        }

        stage("Building image") {
            PipelineContextHolder.buildStrategy.createImage(dockerfile, params.tag)
        }

    }

}