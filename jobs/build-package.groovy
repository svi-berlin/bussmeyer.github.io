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
            // Package

            // Post Build
                // Activate Chuck Norris
                // Artefakte archivieren
                // Send artifacts over SSH?
        }
        chucknorris {}
    }
}
