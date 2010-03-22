<%--
  Created by IntelliJ IDEA.
  User: sgo
  Date: Mar 22, 2010
  Time: 8:33:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple GSP page</title></head>
  <body>
    <g:xwikiRender syntax="confluence/1.0">h1. Wiki Header

Some blabla under the header

{test}

{test:who=Grails}
    </g:xwikiRender>

    ${'''h1. Another Wiki Header

And some text under the header'''.renderAsXHTML('confluence/1.0')}
  </body>
</html>