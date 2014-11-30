// Creates a View for the Project
def project = 'Bussmeyer/bussmeyer.github.io'
view {
    name("${project}")
    jobs {
        regex(".*${project}.*")
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
