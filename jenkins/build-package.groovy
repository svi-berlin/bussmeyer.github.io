// https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL

def project = 'Bussmeyer/bussmeyer.github.io'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
    def branchName = it.name
    job {
        name "PP Test - 1 Developer Jobs - Basic Build and Package - ${project} - ${branchName}".replaceAll('/','-')
        scm {
            git("git://github.com/${project}.git", branchName)
            // Github Repo Browser
            // Calculate changelog against a specific branch
            // Wipe out repository & force clone
        }
        steps {
            //maven("test -Dproject.name=${project}/${branchName}")
            // Build
            def fpmCommand = "fpm -s dir -t rpm --name '${project}' --version 1 --iteration ${BUILD_NUMBER} --description 'Static Test Site' --maintainer 'thomas.bussmeyer@pixelpark.com' --vendor 'admin@pixelpark.com' --url 'http://www.pixelpark.com' --log info --verbose '${WORKSPACE}/src'".replaceAll('/','-')
            shell(fpmCommand)
            // Package
                // Persist artefacts somewhere.
            // Post Build
                // Activate Chuck Norris
                // Artefakte archivieren
                // Send artifacts over SSH?
        }
        publishers {
            chucknorris()
        }
    }
}
