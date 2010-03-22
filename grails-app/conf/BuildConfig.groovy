grails.project.dependency.resolution = {
    inherits "global" // inherit Grails' default dependencies
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
//        mavenRepo "http://repository.codehaus.org"
//        //mavenRepo "http://download.java.net/maven/2/"
//        mavenRepo "http://repository.jboss.com/maven2/"
        mavenRepo "http://maven.xwiki.org/releases/"
        mavenRepo "http://maven.xwiki.org/externals/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile 'org.xwiki.platform:xwiki-core-rendering-parser-wikimodel:2.0.5', {
            excludes 'commons-logging', 'xml-apis'
        }
        compile 'org.xwiki.platform:xwiki-core-component-default:2.0.5', {
            excludes 'commons-logging', 'xml-apis'
        }
        compile 'org.xwiki.platform:xwiki-core-properties:2.0.5', {
            excludes 'commons-logging', 'xml-apis'
        }
        runtime 'org.xwiki.platform:xwiki-core-rendering-macro-toc:2.0.5', {
            excludes 'commons-logging', 'xml-apis'
        }
        runtime 'org.wikimodel:wem:2.0.3-20080320'
    }
}