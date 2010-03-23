package org.groovy.grails.plugins.xwiki.rendering

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
class RenderAsXhtmlMethodTests extends GroovyTestCase {

    void testRenderConfluenceMarkup() {
        def result = 'This is *bold*'.renderAsXHTML('confluence/1.0')
        assert result == '<p>This is <strong>bold</strong></p>'
    }

    void testMacroNoParams() {
        def result = '{test}'.renderAsXHTML('confluence/1.0')
        assert result == '<p>Hello Wiki!</p>'
    }

    void testMacroWithParams() {
        def result = '{test:who=Test}'.renderAsXHTML('confluence/1.0')
        assert result == '<p>Hello Test!</p>'
    }
}
