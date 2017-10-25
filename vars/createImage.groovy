import com.zenika.tz.demo.PipelineContextHolder

def call(String dockerFile) {

    String cmds = null
    if(dockerFile instanceof GString) {
        cmds = instructions
    } else {
        cmds = readFile(file: dockerFile)
    }

    PipelineContextHolder.buildStrategy.createImage(cmds)
}