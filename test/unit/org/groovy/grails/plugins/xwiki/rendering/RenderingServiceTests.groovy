package org.groovy.grails.plugins.xwiki.rendering

import org.xwiki.component.embed.EmbeddableComponentManager
import org.xwiki.component.manager.ComponentManager

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
class RenderingServiceTests extends GroovyTestCase {

    RenderingService renderer

    protected void setUp() {
        super.setUp()

        ComponentManager componentManager = new EmbeddableComponentManager()
        componentManager.initialize getClass().classLoader

        renderer = new RenderingService(componentManager:componentManager)
    }

    void testRenderConfluenceMarkup() {
        def input = 'This is *bold*'
        def output = renderer.renderAsXHTML('confluence/1.0', input)
        assert output == '<p>This is <strong>bold</strong></p>'
    }
}
