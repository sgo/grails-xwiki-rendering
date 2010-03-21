package org.groovy.grails.plugins.xwiki.rendering.services

import org.xwiki.rendering.block.XDOM
import org.xwiki.rendering.listener.Listener
import org.xwiki.rendering.parser.Parser
import org.xwiki.rendering.parser.Syntax
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter
import org.xwiki.rendering.renderer.PrintRendererFactory
import org.xwiki.rendering.renderer.Renderer
import org.xwiki.rendering.transformation.TransformationManager

class RenderingService {

    boolean transactional = false

    def componentManager

    String renderAsXHTML(String syntax, String input) {
        // Parse xwiki 2.0 syntax
        Parser parser = (Parser) componentManager.lookup(Parser.class, syntax)
        XDOM xdom = parser.parse(new StringReader(input))

        // Run macros
//        TransformationManager txManager = (TransformationManager) componentManager.lookup(TransformationManager.class)
//        txManager.performTransformations(xdom, parser.syntax)

        // Generate XHTML
        PrintRendererFactory rf = componentManager.lookup(PrintRendererFactory.class);
        DefaultWikiPrinter printer = new DefaultWikiPrinter()
        Renderer htmlRenderer = rf.createRenderer(Syntax.XHTML_1_0, printer);

        // Perform the rendering
        xdom.traverse(htmlRenderer);

        return printer.toString()
    }
}
