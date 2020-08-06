package top.imwonder.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.util.IdUtil;

@Slf4j
@Component
public class SystemProperties {

    @Autowired
    private JdbcTemplate jt;

    private String iconfontUrl;

    public String getIconfontUrl() {
        if (iconfontUrl == null) {
            try {
                iconfontUrl = jt.queryForObject("select w_value from w_system_properties where w_name = 'iconfontUrl'", String.class);
            } catch (Exception e) {
                log.debug("can not found propertie 'iconfontUrl'");
                jt.update("insert into w_system_properties (w_id, w_name, w_value) value(?,?,?)", IdUtil.uuid(),
                        "iconfontUrl", "");
                iconfontUrl = "";
            }
        }
        return this.iconfontUrl;
    }

    public void setIconfontUrl(String iconfontUrl){
        try {
            jt.update("update w_system_properties set w_value = ? where w_name = 'iconfontUrl'", iconfontUrl);
            this.iconfontUrl = iconfontUrl;
        } catch (Exception e) {
            log.debug("can not found propertie 'iconfontUrl'");
            jt.update("insert into w_system_properties (w_id, w_name, w_value) value(?,?,?)", IdUtil.uuid(),
                    "iconfontUrl", "");
            iconfontUrl = "";
        }

    }
}