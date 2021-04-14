/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:58:02 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:58:02 
 */
package top.imwonder.myblog.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractDomain {

    private String id;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String email;

    private String qq;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birth;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regDate;

    private Boolean disable;
}