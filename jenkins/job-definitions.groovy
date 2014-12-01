def project = 'Bussmeyer/bussmeyer.github.io'
def projectFilter = "${project}".replaceAll('/','-')
def branchApi = new URL("https://api.github.com/repos/${project}/branches")
def branches = new groovy.json.JsonSlurper().parse(branchApi.newReader())
def environments = ['Dev', 'Test', 'Live']

// Creates a View for the Project
view {
    name project.replaceAll('/','-')
    jobs {
        regex(".*${projectFilter}.*")
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

// https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL
// https://github.com/jenkinsci/job-dsl-plugin/wiki/Job-DSL-Commands
// Namesschema ändern auf project - 1 Developer Jobs - Basic Build and Package -  branchName
// Vars wie project über alle job sharen. Wie macht man einen include in groovy?
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
        // Package
            def fpmCommandBasics = "fpm -s dir -t rpm --name ${project}-${branchName}".replaceAll('/','-')
            def fpmCommandVersions = '--version 1 --iteration ${BUILD_NUMBER}'
            def fpmCommandLogs = "--log info --verbose"
            def fpmCommandDesc = '--description "${GIT_COMMIT}\n${GIT_BRANCH}\n${GIT_URL}\n${GIT_AUTHOR_EMAIL}\n${GIT_COMMITTER_EMAIL}"'
            def fpmCommandProject = '--maintainer "thomas.bussmeyer@pixelpark.com" --vendor "admin@pixelpark.com" --url "http://www.pixelpark.com" "${WORKSPACE}/src"'
            shell("${fpmCommandBasics} ${fpmCommandVersions} ${fpmCommandLogs} ${fpmCommandDesc} ${fpmCommandProject}")

            // mv "${WORKSPACE}/Bussmeyer-bussmeyer.github.io-develop-1-${BUILD_NUMBER}.x86_64.rpm" /var/www/repo.local/artefacts
            shell('mv "\"${WORKSPACE}\"/${project}-${branchName}-1-\"${BUILD_NUMBER}\".x86_64.rpm" /var/www/repo.local/artefacts')

        }
        publishers {
            chucknorris()
            // Artefakte archivieren
        }
    }
}

// Copy Artifacts
// Ein zentraler Job, der nachgelagert nach allen Buildjobs ausgeführt wird und die Artefakte an eine zentrale Stelle kopiert.

// scp Bussmeyer-bussmeyer.github.io-1-13.x86_64.rpm vagrant@192.168.2.202:/var/www/repo.local/artefacts-6.5/
// crontab anstoßen: /usr/bin/createrepo --cachedir /var/cache/repo.local/artefacts-6.5 --changelog-limit 5 --update /var/www/repo.local/artefacts-6.5 1>/dev/null


// Deploy definitions
// Parameter
    // Environment
    // Branch
    // Artefakt/Buildnummer
// Wenn Environment ein Param ist, dann reicht vielleicht auch ein Job?
// Auf dem Zielserver:
    // # yum clean expire-cache
    // und
    // yum update Bussmeyer-bussmeyer.github.io-1-11
    // yum downgrade Bussmeyer-bussmeyer.github.io-1-11
    // oder
    // yum remove Bussmeyer-bussmeyer.github.io
    // yum install Bussmeyer-bussmeyer.github.io-1-11
environments.each {
    job {
        name "${project} - 2 Deployment Jobs - Deploy to Dev".replaceAll('/','-')
        steps {
            //maven("test -Dproject.name=${project}/${branchName}")
        }
        publishers {
            chucknorris()
        }
    }
}

// Database syncs