def project = 'Bussmeyer/bussmeyer.github.io'
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
branches.each {
    def branchName = it.name
    job {
        name "PP Test - 1 Developer Jobs - Basic Build and Package - ${project} - ${branchName}".replaceAll('/','-')
        scm {
            git("git://github.com/${project}.git", branchName)
        }
        steps {
            //maven("test -Dproject.name=${project}/${branchName}")
        }
    }
}
