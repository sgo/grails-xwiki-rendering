package org.groovy.grails.plugins.xwiki.renderer

import org.xwiki.rendering.transformation.MacroTransformationContext
import org.xwiki.rendering.block.Block
import org.xwiki.properties.BeanManager
import org.xwiki.rendering.macro.AbstractMacro

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
class GrailsMacro extends AbstractMacro {

    private Macro macro

    GrailsMacro(GrailsMacroClass clazz, Macro macro, BeanManager beanManager) {
        super(clazz.logicalPropertyName, toMacroDescription(macro), macro.parameterClass)
        this.macro = macro
        this.beanManager = beanManager
    }

    boolean supportsInlineMode() {macro.supportsInlineMode()}

    List<Block> execute(Object p, String s, MacroTransformationContext macroTransformationContext) {
        macro.execute p, s, macroTransformationContext
    }

    static String toMacroDescription(Macro macro) {
        if(macro.description) return macro.description
        else return "${getClass().simpleName}> wrapped ${macro.getClass().name}"
    }

    int compareTo(Object o) {
        this <=> o
    }
}
