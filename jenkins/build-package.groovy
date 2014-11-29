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
            // Build
            def fpmCommandBasics = "fpm -s dir -t rpm --name ${project}".replaceAll('/','-')
            def fpmCommandVersions = '--version 1 --iteration ${BUILD_NUMBER}'
            def fpmCommandLogs = "--log info --verbose"
            def fpmCommandDesc = '--description "${GIT_COMMIT}\n${GIT_BRANCH}\n${GIT_URL}\n${GIT_AUTHOR_EMAIL}\n${GIT_COMMITTER_EMAIL}"'
            def fpmCommandProject = '--description "Static Test Site" --maintainer "thomas.bussmeyer@pixelpark.com" --vendor "admin@pixelpark.com" --url "http://www.pixelpark.com" "${WORKSPACE}/src"'
            shell("${fpmCommandBasics} ${fpmCommandVersions} ${fpmCommandLogs} ${fpmCommandDesc} ${fpmCommandProject}")
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
