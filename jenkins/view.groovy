// Creates a View for the Project
def project = 'Bussmeyer/bussmeyer.github.io'
view {
    name "${project}" .replaceAll('/','-')
    jobs {
        regex(".*${project}.*").replaceAll('/','-')
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
