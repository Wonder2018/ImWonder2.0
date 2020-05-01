/*
 * @Author: Wonder2019 
 * @Date: 2020-05-01 12:00:18 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-01 12:00:18 
 */
package top.imwonder.myblog.login;

import lombok.Getter;

public enum WonderRedirectEnum {
    PASS(200, "继续执行"), SKIP(302, "跳过登录"), RESET(201, "重置状态");

    @Getter
    private int code;

    @Getter
    private String description;

    WonderRedirectEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}