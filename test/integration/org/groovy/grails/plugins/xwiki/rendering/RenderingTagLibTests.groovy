package org.groovy.grails.plugins.xwiki.rendering

import grails.test.GroovyPagesTestCase

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
class RenderingTagLibTests extends GroovyPagesTestCase {

    void testRenderConfluenceMarkup() {
        def template = '<g:xwikiRender syntax="confluence/1.0">This is *bold*</g:xwikiRender>'
        assertOutputEquals '<p>This is <strong>bold</strong></p>', template
    }

    void testMacroNoParams() {
        def template = '<g:xwikiRender syntax="confluence/1.0">{test}</g:xwikiRender>'
        assertOutputEquals '<p>Hello Wiki!</p>', template
    }

    void testMacroWithParams() {
        def template = '<g:xwikiRender syntax="confluence/1.0">{test:who=Test}</g:xwikiRender>'
        assertOutputEquals '<p>Hello Test!</p>', template
    }
}
