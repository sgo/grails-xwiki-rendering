import org.xwiki.component.embed.EmbeddableComponentManager

class XwikiRenderingGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
//    def grailsVersion = "1.1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Luis Arias"
    def authorEmail = "luis.arias@pilango.com"
    def title = "Provides XWiki Rendering in Grails"
    def description = '''\\
The XWiki Rendering Plugin currently provides a xwikiRender gsp tag that allows
you to embed any of the various wiki syntaxes that are supported.  See here to
learn more about the XWiki Rendering Module and its features:

http://code.xwiki.org/xwiki/bin/view/Modules/RenderingModule
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/XwikiRendering+Plugin"

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
        componentManager(EmbeddableComponentManager, getClass().classLoader) {
        }
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
        def componentManager = applicationContext.getBean('componentManager')
        componentManager.initialize()
    }

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
