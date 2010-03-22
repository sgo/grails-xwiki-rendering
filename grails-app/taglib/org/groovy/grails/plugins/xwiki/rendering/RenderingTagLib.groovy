package org.groovy.grails.plugins.xwiki.rendering

/**
*  TagLib providing access to syntax rendering and transformation through the
*  XWiki Rendering Module.
*  
*  @author Luis Arias <luis.arias@pilango.com>
*/
class RenderingTagLib {

    def renderingService

    /**
    * @param attrs.syntax Determines the parser that will be used to parse the tag body.
    *                     Can currently be one of the following
    *                     <ul>
    *                       <li>xwiki/2.0</li>
    *                       <li>confluence/1.0</li>
    *                       <li>creole/1.0</li>
    *                       <li>jspwiki/1.0</li>
    *                       <li>mediawiki/1.0</li>
    *                     </ul>
    *                     The default value is xwiki/2.0.
    */
    def xwikiRender = {attrs, body ->
        // Make xwiki 2.0 the default syntax
        def syntax = attrs.syntax ?: "xwiki/2.0"

        out << renderingService.renderAsXHTML(syntax, "${body()}")
    }

}
