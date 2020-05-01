package top.imwonder.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class DAOTemplate<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jt;

    protected String insertSQL;
    protected String deleteSQL;
    protected String updateSQL;
    protected String updateSqlWithPk;
    protected String loadOneSQL;
    protected String loadMoreSQL;

    protected boolean insertPK = true;
    protected Class<T> clazz;
    private Method[] pkGetters;
    private Method[] pkSetters;
    private Method[] comGetters;
    private Method[] comSetters;
    protected String[] pkColumns;
    protected String[] comColumns;
    protected String tableName;

    public JdbcTemplate getJt(){
        return jt;
    }

    protected void init() {
        
    }

    protected void initColumns() {
        
    }

    protected void buildSQLs() {
        buildInsertSQL();
        buildDeleteSQL();
        buildUpdateSQL(false);
        buildUpdateSQL(true);
        buildLoadOneSQL();
        buildLoadMoreSQL();
    }

    protected void buildInsertSQL() {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into ");
        sqlBuffer.append(tableName);
        sqlBuffer.append("(");
        if(insertPK) {
            for (String pk:pkColumns) {
                sqlBuffer.append(pk);
                sqlBuffer.append(", ");
            }
        }
        for(String cc:comColumns){
            sqlBuffer.append(cc);
            sqlBuffer.append(" ,");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        sqlBuffer.append(") values(");
        int len = insertPK ? pkColumns.length + comColumns.length : comColumns.length;
        for(int i = 0; i < len; i++){
            sqlBuffer.append("?, ");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        sqlBuffer.append(")");
        insertSQL = sqlBuffer.toString();
    }

    protected void buildDeleteSQL(){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("delete from ");
        sqlBuffer.append(tableName);
        buildWhereClause(sqlBuffer);
        deleteSQL = sqlBuffer.toString();
    }

    protected void buildUpdateSQL(boolean updatePk){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("update ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" set ");
        for(String cc:comColumns){
            sqlBuffer.append(cc);
            sqlBuffer.append("=?, ");
        }
        if(updatePk){
            for(String pk:pkColumns){
                sqlBuffer.append(pk);
                sqlBuffer.append("=?, ");
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        buildWhereClause(sqlBuffer);
        if(updatePk) {
        	updateSqlWithPk = sqlBuffer.toString();
        }else {
        	updateSQL = sqlBuffer.toString();
        }
    }

    protected void buildLoadOneSQL(){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select ");
        addSelectProjections(sqlBuffer);
        sqlBuffer.append(" from ");
        sqlBuffer.append(tableName);
        buildWhereClause(sqlBuffer);
        loadOneSQL = sqlBuffer.toString();
    }

    protected void buildLoadMoreSQL(){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select ");
        addSelectProjections(sqlBuffer);
        sqlBuffer.append(" from ");
        sqlBuffer.append(tableName);
        loadMoreSQL = sqlBuffer.toString();
    }

    // TODO Test 'foreach deleteCharAt' and 'if case' performance
    protected void buildWhereClause(StringBuffer buffer){
        buffer.append(" where ");
        for(int i = 0;i < pkColumns.length;i ++){
            if(i == 0){
                buffer.append(pkColumns[i]);
                buffer.append("=?");
            }else{
                buffer.append(" and ");
                buffer.append(pkColumns[i]);
                buffer.append("=?");
            }
        }
    }

    private void addSelectProjections(StringBuffer buffer){
        for (String pk:pkColumns) {
            buffer.append(pk);
            buffer.append(", ");
        }
        for(String cc:comColumns){
            buffer.append(cc);
            buffer.append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 2);
    }

    public void insert(T t){
        jt.update(insertSQL, getInsertParamValues(t));
    }

    public void delete(Object i){
        delete(new Object[]{i});
    }

    public void delete(Object[] i) {
        jt.update(deleteSQL, i);
    }

    public void update(T t) {
        jt.update(updateSQL, getUpdateParamValues(t, null));
    }

    public void updateWithPk(T t, Object[] oldPks) {
        jt.update(updateSqlWithPk, getUpdateParamValues(t, oldPks));
    }
    
    public ArrayList<T> loadMore(String clause, Object[] i) {
        return jt.query(appendClause(loadMoreSQL, clause), i, new ResultSetExtractor<ArrayList<T>>(){
            public ArrayList<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<T> result = new ArrayList<T>();
                while(rs.next())
                    result.add(wrapResult(rs));
                return result;
            }

        });
    }

    public T loadOne(Object i) {
        return loadOne(new Object[]{i});
    }


    public T loadOne(Object[] i) {
        return jt.query(loadOneSQL, i, new ResultSetExtractor<T>(){
            public T extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next())
                    return wrapResult(rs);
                return null;
            }

        });
    }

    protected Object[] getInsertParamValues(T t){
        int len = insertPK ? pkColumns.length + comColumns.length : comColumns.length;
        Object[] objects = new Object[len];
        try {
            int i = 0;
            if (insertPK) {
                for (Method method : pkGetters) {
                    objects[i++] = method.invoke(t);
                }
            }
            for (Method method : comGetters) {
                objects[i++] = method.invoke(t);
            }
        }catch(Exception ex){
            throw new RuntimeException("An error occurred (Insert)", ex);
        }
        return objects;
    }

    protected Object[] getUpdateParamValues(T t, Object[] pks){
        Object[] objects = new Object[pkColumns.length + comColumns.length + (pks == null ? 0 : pks.length)];
        try {
            int i = 0;
            for (Method method : comGetters) {
                objects[i++] = method.invoke(t);
            }
            for (Method method : pkGetters) {
                objects[i++] = method.invoke(t);
            }
            if(pks != null && pks.length>0){
                for (Object pk : pks) {
                    objects[i++] = pk;
                }
            }
        }catch(Exception ex){
            throw new RuntimeException("An error occurred (Update)", ex);
        }
        return objects;
    }

    protected String appendClause(String sql, String clause){
        if(clause == null)
            return sql;
        return sql + " " + clause.trim();
    }

    protected T wrapResult(ResultSet rs) throws SQLException{
        int startIndex=1;
        try{
            T obj = clazz.newInstance();
            for (Method method: pkSetters) {
                method.invoke(obj, getObjectFromRs(method, rs, startIndex++));
            }
            for (Method method: comSetters) {
                method.invoke(obj, getObjectFromRs(method, rs, startIndex++));
            }
            return obj;
        }catch(Exception ex){
            if(ex instanceof SQLException)
                throw (SQLException) ex;
            throw new RuntimeException("An error occurred (LoadData)", ex);
        }
    }

    private Object getObjectFromRs(Method m, ResultSet rs, int i) throws SQLException {
        Class<?> type = m.getParameterTypes()[0];
        Class<?> st = type.getSuperclass();
        if(st == null){//基本数据类型
            char[] narr = type.getName().toCharArray();
            narr[0] = (char)(narr[0] - 32);
            try{
                return ResultSet.class.getMethod("get" + new String(narr), int.class).invoke(rs, i);
            }catch(Exception ex){
                log.info("method name:{}", new String(narr));
                log.warn(ex.getMessage(), ex);
            }
        }else if(st == Number.class){//基本数据类型的包装类
            String n = type.getSimpleName();
            Object result = null;
            if(n.startsWith("Int")){
                result = rs.getInt(i);
            }else{
                try{
                    result = ResultSet.class.getMethod("get" + n, int.class).invoke(rs, i);
                }catch(Exception ex){
                    log.warn(ex.getMessage(), ex);
                }
            }
            return rs.wasNull() ? null : result;
        }
        if(type == Date.class){
            return rs.getTimestamp(i);
        }
        return rs.getObject(i);
    }
}