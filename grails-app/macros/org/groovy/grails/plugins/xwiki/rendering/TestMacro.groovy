package org.groovy.grails.plugins.xwiki.rendering

import org.xwiki.rendering.block.WordBlock
import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block
import org.groovy.grails.plugins.xwiki.renderer.MacroAdapter
import org.xwiki.rendering.block.ParagraphBlock

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
class TestMacro extends MacroAdapter {
    Class parameterClass = TestMacroParameters

    def List<Block> execute(def params, String content, MacroTransformationContext context) {
        [new ParagraphBlock([new WordBlock("Hello $params.who!")])]
    }
}

class TestMacroParameters {

    String who = 'Wiki'
}