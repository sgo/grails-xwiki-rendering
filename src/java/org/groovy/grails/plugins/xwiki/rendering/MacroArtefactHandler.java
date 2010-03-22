package org.groovy.grails.plugins.xwiki.rendering;

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
public class MacroArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "Macro";

    public MacroArtefactHandler() {
        super(TYPE, GrailsMacroClass.class, DefaultGrailsMacroClass.class, TYPE);
    }

    public boolean isArtefactClass(Class clazz) {
        return clazz != null && Macro.class.isAssignableFrom(clazz);
    }
}
