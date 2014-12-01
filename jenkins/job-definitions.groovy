def project = 'Bussmeyer/bussmeyer.github.io'
def projectFiltered = "${project}".replaceAll('/','-')
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
def environments = [[id: "1", name: "Dev", host:"web.local"], [id: "2", name: "Test", host:"web.local"], [id: "3", name: "Live", host:"web.local"]]

// Creates a View for the Project
view {
    name project.replaceAll('/','-')
    jobs {
        regex(".*${projectFiltered}.*")
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

// https://github.com/jenkinsci/job-dsl-plugin/wiki/Job-DSL-Commands
// Namesschema ändern auf project - 1 Developer Jobs - Basic Build and Package -  branchName
branches.each {
    def branchName = it.name
    job {
        name "${project} - 1 Developer Jobs - Basic Build and Package - ${branchName}".replaceAll('/','-')
        scm {
            git("git://github.com/${project}.git", branchName)
            // Github Repo Browser einstellen
            // Calculate changelog against a specific branch
            // Wipe out repository & force clone
        }
        steps {
        // Build
            // Package with fpm
            def fpmCommandBasics = "fpm -s dir -t rpm --name ${project}-${branchName}".replaceAll('/','-')
            def fpmCommandVersions = '--version 1 --iteration ${BUILD_NUMBER}'
            def fpmCommandLogs = "--log info --verbose"
            def fpmCommandDesc = '--description "${GIT_COMMIT}\n${GIT_BRANCH}\n${GIT_URL}\n${GIT_AUTHOR_EMAIL}\n${GIT_COMMITTER_EMAIL}"'
            def fpmCommandProject = '--maintainer "thomas.bussmeyer@pixelpark.com" --vendor "admin@pixelpark.com" --url "http://www.pixelpark.com" "${WORKSPACE}/src"'
            shell("${fpmCommandBasics} ${fpmCommandVersions} ${fpmCommandLogs} ${fpmCommandDesc} ${fpmCommandProject}")

            // Move to repo.
            def workspace = '${WORKSPACE}'
            def buildNumber = '${BUILD_NUMBER}'
            shell('mv "' + workspace + '/' + projectFiltered + '-' + branchName + '-1-' + buildNumber + '.x86_64.rpm" /var/www/repo.local/artefacts')
            shell("/usr/bin/createrepo --cachedir /var/cache/repo.local/artefacts --changelog-limit 5 --update /var/www/repo.local/artefacts 1>/dev/null")
        }
        publishers {
            chucknorris()
            // Artefakte archivieren
        }
    }
}

// Environment
// Branch
// Artefakt/Buildnummer
environments.eachWithIndex {
    def environmentId = it.id
    def environmentName = it.name
    job {
        name "${project} - 2.${environmentId} Deployment Jobs - Deploy to ${environmentName}".replaceAll('/','-')
        steps {
            shell("yum clean expire-cache")
            shell("yum remove Bussmeyer-bussmeyer.github.io")
            shell("yum install Bussmeyer-bussmeyer.github.io-1-11")
        }
        publishers {
            chucknorris()
        }
    }
}

// Database syncs
