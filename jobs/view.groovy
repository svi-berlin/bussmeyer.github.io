// Creates a View for the Project
view {
    // common options
    name("PP Test Projekt")
    jobs {
        regex(".*PP Test.*")
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
