package top.imwonder.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.util.annotation.DomainName;

@Slf4j
public abstract class AbstractDomain {

    public void copyFrom(Object obj, boolean isCover) {
        Field fields[] = obj.getClass().getDeclaredFields();
        try {
            copyFields(obj, fields, isCover);
        } catch (ReflectiveOperationException e) {
            log.info(MessageUtil.getMsg("error.unexpected"));
            log.debug(MessageUtil.getMsg("error.debug.simple", e.getMessage()));
            e.printStackTrace();
        }
    }

    public void copyFields(Object obj, Field[] fields, boolean isCover) throws ReflectiveOperationException {
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                copyOneField(obj, field, isCover);
            } catch (NoSuchMethodException | NoSuchFieldException e) {
                field.setAccessible(accessible);
                continue;
            } catch (ReflectiveOperationException e) {
                field.setAccessible(accessible);
                throw e;
            }
        }
    }

    private void copyOneField(Object obj, Field objField, boolean isCover) throws ReflectiveOperationException {
        Object objValue = getFieldValue(obj, objField);
        Field targetField = getTargetField(objField);
        boolean accessible = targetField.isAccessible();
        targetField.setAccessible(true);
        try {
            if (isCover) {
                setFieldValue(targetField, objValue);
                return;
            }
            Object targetValue = getFieldValue(this, targetField);
            if (targetValue == null && objValue != null) {
                setFieldValue(targetField, objValue);
            }
        } catch (ReflectiveOperationException e) {
            targetField.setAccessible(accessible);
            throw e;
        }
    }

    private Object getFieldValue(Object obj, Field field) throws ReflectiveOperationException {
        Method getter = findGetter(obj.getClass(), field);
        return getter.invoke(obj);
    }

    private void setFieldValue(Field field, Object value) throws ReflectiveOperationException {
        Method setter = findSetter(field);
        setter.invoke(this, value);
    }

    private Method findGetter(Class<?> type, Field field) throws NoSuchMethodException {
        String methodName = genGetterName(field.getName());
        return type.getMethod(methodName);
    }

    private Method findSetter(Field field) throws NoSuchMethodException {
        String methodName = genSetterName(field.getName());
        return this.getClass().getMethod(methodName, field.getType());
    }

    private Field getTargetField(Field objField) throws NoSuchFieldException {
        String targtFieldName = getTargetFieldName(objField);
        return this.getClass().getDeclaredField(targtFieldName);
    }

    private String getTargetFieldName(Field field) {
        if (field.isAnnotationPresent(DomainName.class)) {
            DomainName domainName = field.getAnnotation(DomainName.class);
            if (domainName.getClass().equals(getClass())) {
                return domainName.name();
            }
        }
        return field.getName();
    }

    private String genGetterName(String fieldName) {
        return String.format("get%s", StringUtil.upperFirstChar(fieldName));
    }

    private String genSetterName(String fieldName) {
        return String.format("set%s", StringUtil.upperFirstChar(fieldName));
    }

}
