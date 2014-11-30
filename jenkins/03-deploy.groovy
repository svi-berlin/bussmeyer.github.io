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


job {
    name "PP Test - 2 Deployment Jobs - Deploy to Dev".replaceAll('/','-')
    steps {
        //maven("test -Dproject.name=${project}/${branchName}")
    }
    publishers {
        chucknorris()
    }
}

job {
    name "PP Test - 2 Deployment Jobs - Deploy to Test".replaceAll('/','-')
    steps {
        //maven("test -Dproject.name=${project}/${branchName}")
    }
    publishers {
        chucknorris()
    }
}

job {
    name "PP Test - 2 Deployment Jobs - Deploy to Live".replaceAll('/','-')
    steps {
        //maven("test -Dproject.name=${project}/${branchName}")
    }
    publishers {
        chucknorris()
    }
}
