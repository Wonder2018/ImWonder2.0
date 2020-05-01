package top.imwonder.myblog.login.util;

import javax.servlet.http.HttpServletRequest;

import top.imwonder.myblog.exception.WonderIllegalStateException;
import top.imwonder.myblog.login.WonderRedirectEnum;
import top.imwonder.util.StringUtil;

public class LoginUtil {
    public static WonderRedirectEnum checkLonginState(HttpServletRequest req,String callFrom) {
        String user = req.getRemoteUser();
        String loginFrom = (String)req.getSession().getAttribute("loginFrom");
        if(StringUtil.isAllNotEmpty(user,loginFrom)){
            if(loginFrom.contains(callFrom)){
                return WonderRedirectEnum.SKIP;
            }else{
                return WonderRedirectEnum.RESET;
            }
        }else if(!StringUtil.isAllEmpty(user,loginFrom)){
            throw new WonderIllegalStateException("501", "登录状态不正确，请重新登录！", true);
        }
        return WonderRedirectEnum.PASS;
    }
}