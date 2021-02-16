package top.imwonder.myblog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import top.imwonder.util.IdUtil;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SystemProperties {

    private static final String DELETE_SQL = "delete from w_system_properties where w_name=?";

    private static final String INSERT_SQL = "insert into w_system_properties (w_id, w_name, w_value, w_order) value(?,?,?)";

    private static final String SELECT_SQL = "select w_value from w_system_properties where w_name=? order by w_order asc";

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private Gson gson;

    private String iconfontUrl;

    private Map<String, String> buckets;

    public String getIconfontUrl() {
        if (iconfontUrl == null) {
            return getIconfontUrl(true);
        }
        return this.iconfontUrl;
    }

    public String getIconfontUrl(boolean reload) {
        if (reload || iconfontUrl == null) {
            this.iconfontUrl = load("iconfontUrl");
        }
        return this.iconfontUrl;
    }

    public void setIconfontUrl(String iconfontUrl) {
        save("iconfontUrl", iconfontUrl);
        this.iconfontUrl = iconfontUrl;
    }

    public String getBucket(String prefix) {
        if (buckets == null) {
            return getBucket(prefix, true);
        }
        return this.buckets.get(prefix);
    }

    public String getBucket(String prefix, boolean reload) {
        if (reload || buckets == null) {
            String json = load("bucketMap");
            if (json.isEmpty()) {
                this.buckets = new HashMap<>();
                save("bucketMap", "{}");
            } else {
                this.buckets = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
                }.getType());
            }
        }
        return this.buckets.get(prefix);
    }

    public void setBucket(String prefix, String bucket) {
        this.buckets.put(prefix, bucket);
        save("bucketMap", gson.toJson(buckets));
    }

    @Transactional
    private String[] save(String name, String value) {
        String vs[] = value.split("(?<=\\G.{4})");
        jt.update(DELETE_SQL, name);
        int i = 0;
        for (String string : vs) {
            jt.update(INSERT_SQL, IdUtil.uuid(), name, string, i++);
        }
        return vs;
    }

    @Transactional
    private String load(String name) {
        List<String> values = jt.queryForList(SELECT_SQL, String.class, name);
        if (values.size() == 0) {
            return "";
        }
        if (values.size() == 1) {
            return values.get(0);
        }
        StringBuffer val = new StringBuffer();
        for (String value : values) {
            val.append(value);
        }
        return val.toString();
    }
}