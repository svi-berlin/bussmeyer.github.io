// Parameter
    // Environment
    // Artefakt



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
