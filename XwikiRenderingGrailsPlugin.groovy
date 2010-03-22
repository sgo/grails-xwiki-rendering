import org.xwiki.component.embed.EmbeddableComponentManager
import org.slf4j.Logger
import org.groovy.grails.plugins.xwiki.rendering.GrailsMacroClass
import org.springframework.beans.factory.config.MethodInvokingFactoryBean
import org.groovy.grails.plugins.xwiki.rendering.MacroArtefactHandler
import org.groovy.grails.plugins.xwiki.rendering.MacroAdapter
import org.groovy.grails.plugins.xwiki.rendering.Macro

import org.apache.commons.lang.WordUtils
import org.xwiki.component.descriptor.DefaultComponentDescriptor
import org.xwiki.properties.BeanManager
import org.groovy.grails.plugins.xwiki.rendering.GrailsMacro
import org.groovy.grails.plugins.xwiki.rendering.RenderingService
import org.groovy.grails.plugins.xwiki.rendering.RenderingService

class XwikiRenderingGrailsPlugin {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(XwikiRenderingGrailsPlugin)

    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def artefacts = [MacroArtefactHandler]
    def watchedResources = [
            'file:./grails-app/macros/**/*Macro.groovy'
    ]

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
    def documentation = "http://grails.org/plugins/xwiki-rendering"

    def doWithSpring = {
        if(LOGGER.infoEnabled) LOGGER.info 'Creating XWiki beans...'
        componentManager(EmbeddableComponentManager) {
        }

        application.macroClasses.each {GrailsMacroClass macroClass->
            configureMacroBeans.delegate = delegate
            configureMacroBeans(macroClass)
        }
    }

    def doWithApplicationContext = { ctx ->
        if(LOGGER.infoEnabled) LOGGER.info 'Initializing XWiki component manager...'
        EmbeddableComponentManager manager = ctx.getBean('componentManager') as EmbeddableComponentManager
        manager.initialize getClass().classLoader

        BeanManager beanManager = manager.lookup(BeanManager) as BeanManager
        application.macroClasses.each {GrailsMacroClass macroClass->
            def macro = ctx.getBean(macroClass.propertyName) as Macro
            def grailsMacro = new GrailsMacro(macroClass, macro, beanManager)
            grailsMacro.initialize()
            manager.registerComponent new DefaultComponentDescriptor(implementation:GrailsMacro, role:org.xwiki.rendering.macro.Macro, roleHint:macroClass.logicalPropertyName), grailsMacro
            if(LOGGER.infoEnabled) LOGGER.info "... Installed XWiki macro ${macroClass.logicalPropertyName}!"
        }
    }

    def doWithDynamicMethods = { ctx ->
        if(LOGGER.infoEnabled) LOGGER.info 'Adding XWiki markup transformer to String class'
        RenderingService renderer = ctx.getBean('renderingService') as RenderingService
        String.metaClass.renderAsXHTML = {String syntax->
            renderer.renderAsXHTML syntax, (String)delegate
        }
    }

    def onChange = { event ->
        if(application.isMacroClass(event.source)) {
            if(LOGGER.infoEnabled) LOGGER.info "Macro modified! [$event.source]"

            def macroClass = application.addArtefact(MacroArtefactHandler.TYPE, event.source)
            def fullName = macroClass.fullName
            def classBean = "${fullName}Class"
            def macroBean = "${WordUtils.uncapitalize(macroClass.shortName)}"

            def beans = beans {
                configureMacroBeans.delegate = delegate
                configureMacroBeans(macroClass)
            }

            if(event.ctx) {
                event.ctx.registerBeanDefinition(classBean, beans.getBeanDefinition(classBean))
                event.ctx.registerBeanDefinition(macroBean, beans.getBeanDefinition(macroBean))

                if(LOGGER.infoEnabled) LOGGER.info "Removing old $macroClass.logicalPropertyName macro..."
                EmbeddableComponentManager manager = event.ctx.getBean('componentManager')
                BeanManager beanManager = manager.lookup(BeanManager) as BeanManager
                manager.unregisterComponent org.xwiki.rendering.macro.Macro, macroClass.logicalPropertyName

                if(LOGGER.infoEnabled) LOGGER.info "Installing new $macroClass.logicalPropertyName macro..."
                def macro = event.ctx.getBean(macroClass.propertyName)
                def grailsMacro = new GrailsMacro(macroClass, macro, beanManager)
                grailsMacro.initialize()
                manager.registerComponent new DefaultComponentDescriptor(implementation:GrailsMacro, role:org.xwiki.rendering.macro.Macro, roleHint:macroClass.logicalPropertyName), grailsMacro
                if(LOGGER.infoEnabled) LOGGER.info "... Installed XWiki macro ${macroClass.logicalPropertyName}!"
            }
        }
    }

    def configureMacroBeans = {GrailsMacroClass macroClass->
        def fullName = macroClass.fullName

        "${fullName}Class"(MethodInvokingFactoryBean) {
            targetObject = ref('grailsApplication', true)
            targetMethod = 'getArtefact'
            arguments = [MacroArtefactHandler.TYPE, fullName]
        }

        "${WordUtils.uncapitalize(macroClass.shortName)}"(ref("${fullName}Class")) {bean ->
            bean.factoryMethod = 'newInstance'
            bean.autowire = 'byName'
            bean.scope = 'prototype'
            bean.clazz = Macro

            if(macroClass.clazz in MacroAdapter) {
                if(LOGGER.debugEnabled) LOGGER.debug '... setting MacroAdapter dependencies!'
                applicationTagLib = ref('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
            }
        }

        if(LOGGER.infoEnabled) LOGGER.info "... registered macro bean ${WordUtils.uncapitalize(macroClass.shortName)}!"
    }
}
