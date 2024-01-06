<#assign securityContext = JspTaglibs["http://www.springframework.org/security/tags"] />

<#if securityContext.isAuthenticationAuthenticated()>
    <#assign
    user = securityContext.getAuthentication().getName()
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>

<#--
<#assign
known = Session.SPRING_SECURITY_CONTEXT??
    >

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>-->
