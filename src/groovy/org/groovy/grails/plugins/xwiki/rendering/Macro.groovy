package org.groovy.grails.plugins.xwiki.rendering

import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
interface Macro<T> {

    String getDescription()
    Class getParameterClass()
    boolean supportsInlineMode()
    List<Block> execute(T params, String content, MacroTransformationContext context)
}