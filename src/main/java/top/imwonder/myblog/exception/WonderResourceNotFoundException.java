/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 22:49:21 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-01 12:03:19
 */
package top.imwonder.myblog.exception;

import lombok.Getter;

public class WonderResourceNotFoundException extends WonderException {

    private static final long serialVersionUID = 1L;

    @Getter
    private String code;

    public WonderResourceNotFoundException(String code, String msg) {
        super(code, msg);
        this.code = code;
    }

    public WonderResourceNotFoundException(String code, String msg, Throwable cause) {
        super(code, msg, cause);
        this.code = code;
    }

}