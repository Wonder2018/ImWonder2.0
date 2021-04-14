/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:51 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:57:51 
 */
package top.imwonder.myblog.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends AbstractDomain {

    private String id;

    private String name;

}