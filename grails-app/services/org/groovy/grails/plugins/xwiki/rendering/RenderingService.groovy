package org.groovy.grails.plugins.xwiki.rendering

import org.xwiki.rendering.block.XDOM
import org.xwiki.rendering.parser.Parser
import org.xwiki.rendering.renderer.PrintRendererFactory
import org.xwiki.rendering.renderer.Renderer
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.syntax.Syntax
import org.xwiki.component.manager.ComponentManager
import org.xwiki.rendering.transformation.TransformationManager

class RenderingService {

    static boolean transactional = false

    ComponentManager componentManager

    String renderAsXHTML(String syntax, String input) {
        // parse wiki syntax
        Parser parser = componentManager.lookup(Parser.class, syntax)
        XDOM dom = parser.parse(new StringReader(input))

        // create xhtml rendering
        PrintRendererFactory factory = componentManager.lookup(PrintRendererFactory, Syntax.XHTML_1_0.toIdString()) as PrintRendererFactory
        DefaultWikiPrinter printer = new DefaultWikiPrinter()
        Renderer renderer = factory.createRenderer(printer)

        // handle macro's
        TransformationManager txManager = componentManager.lookup(TransformationManager) as TransformationManager
        txManager.performTransformations dom, parser.syntax

        // perform transformation
        dom.traverse(renderer)
        return printer.toString()
    }
}
