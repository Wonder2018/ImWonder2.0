/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:58 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:57:58 
 */
package top.imwonder.myblog.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermission extends AbstractDomain {

    private String roleId;

    private String permId;

}