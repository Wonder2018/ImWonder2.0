/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 23:21:51 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-01 00:03:19
 */
package top.imwonder.myblog.login;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class WonderFormAuthenticationFilter extends FormAuthenticationFilter {
    private String roleBase;

    public void setRoleBase(String roleBase) {
        this.roleBase = roleBase;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // TODO Auto-generated method stub
        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        // String answer = request.getParameter("captcha");
        boolean correctCaptcha = true;
        // correctCaptcha = MyCaptcha.isans(answer, (HttpServletRequest)request);
        return new WonderUPT(username, password, rememberMe, host, correctCaptcha, roleBase);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.toHttp(request).getSession().setAttribute("loginFrom", getLoginUrl());
        
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        String loginFrom = (String) WebUtils.toHttp(request).getSession().getAttribute("loginFrom");
        String uri = WebUtils.toHttp(request).getRequestURI();
        String loginUrl = getLoginUrl();
        if (loginFrom != null) {
            loginFrom = loginFrom.substring(1, loginFrom.lastIndexOf("/"));
        }
        if ((loginUrl.contains("wonderlandsadmin") && !loginUrl.equals(uri)) && (loginFrom == null || !"wonderlandsadmin".equals(loginFrom))) {
            return false;
        }
        return (subject.isAuthenticated() && subject.getPrincipal() != null) || subject.isRemembered() || (!isLoginRequest(request, response) && isPermissive(mappedValue));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(WebUtils.toHttp(request).getSession().getAttribute("loginFrom") == null){
            return super.onAccessDenied(request, response);
        }
        request.setAttribute("code", "404");
        request.setAttribute("msg", "您请求的资源未找到！");
        request.getRequestDispatcher("/error").forward(request, response);
        return false;
    }
    
}
