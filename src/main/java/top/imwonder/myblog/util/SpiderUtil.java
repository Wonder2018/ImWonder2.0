package top.imwonder.myblog.util;

import top.imwonder.util.StringUtil;

public class SpiderUtil {

    public static boolean isSpider(String ua) {
        String regex = "([\u0021-\u007d]|\\s)*(bot|spider)([\u0021-\u007d]|\\s)*";
        if (StringUtil.isEmpty(ua))
            return false;
        return ua.toLowerCase().matches(regex);
    }
}
