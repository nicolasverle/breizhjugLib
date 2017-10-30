import com.zenika.tz.demo.PipelineContextHolder

def call(Map params) {

    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        String cmds = null
        if(params.script) {
            cmds = params.script
        } else if(params.file) {
            cmds = readFile(file: params.file)
        } else {
            error("Neither docker instructions nor Dockerfile path specified !")
        }

        stage("Building image") {
            PipelineContextHolder.buildStrategy.createImage(cmds)
        }

    }

}