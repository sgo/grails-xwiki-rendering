package org.groovy.grails.plugins.xwiki.rendering

import org.springframework.web.context.request.RequestContextHolder as RCH

/**
 * @author <a href="mailto:sebastian.gozin@gmail.com">sebastian.gozin@gmail.com</a>
 */
abstract class MacroAdapter<T> implements Macro<T> {

    def applicationTagLib

    String getDescription() {null}
    Class getParameterClass() {DefaultParameters}
    boolean supportsInlineMode() {false}
    String createLink(Map attrs) {applicationTagLib.createLink(attrs)}
    def getRequest() {RCH.currentRequestAttributes().currentRequest}
}
