// Creates a View for the Project
def script = new GroovyScriptEngine( '.' ).with {
    loadScriptByName( 'settings.groovy' )
}
this.metaClass.mixin script

getProject()

def project = 'Bussmeyer/bussmeyer.github.io'
def projectFilter = "${project}".replaceAll('/','-')
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
