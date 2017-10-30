import com.zenika.tz.demo.PipelineContextHolder

def call(String dockerFile) {

    node(PipelineContextHolder.buildStrategy.getNodesLabel()) {
        String cmds = null
        if(dockerFile instanceof GString) {
            cmds = instructions
        } else {
            cmds = readFile(file: dockerFile)
        }

        stage("Building image") {
            PipelineContextHolder.buildStrategy.createImage(cmds)
        }

    }

}