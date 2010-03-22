package org.groovy.grails.plugins.xwiki.rendering;

import org.codehaus.groovy.grails.commons.AbstractInjectableGrailsClass;

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
public class DefaultGrailsMacroClass extends AbstractInjectableGrailsClass implements GrailsMacroClass {
    public DefaultGrailsMacroClass(Class clazz) {
        super(clazz, MacroArtefactHandler.TYPE);
    }
}
