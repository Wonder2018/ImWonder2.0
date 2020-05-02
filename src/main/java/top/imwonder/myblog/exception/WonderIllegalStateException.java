/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:58:11 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:58:11 
 */
package top.imwonder.myblog.exception;

import lombok.Getter;

public class WonderIllegalStateException extends WonderException {

    private static final long serialVersionUID = 1L;

    @Getter
    private boolean needReLogin;
    
    public WonderIllegalStateException(String code, String msg, boolean needReLogin) {
        super(code, msg);
        this.needReLogin = needReLogin;
    }

    public WonderIllegalStateException(String code, String msg, boolean needReLogin, Throwable cause) {
        super(code, msg, cause);
        this.needReLogin = needReLogin;
    }

}