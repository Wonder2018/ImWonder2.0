package top.imwonder.myblog.enumeration;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class EnumConverterFactory implements ConverterFactory<String, StringConverterableEnum> {

    private static final Map<Class<?>, Converter<String,? extends StringConverterableEnum>> converterMap = new WeakHashMap<>();

    @Override
    public <T extends StringConverterableEnum> Converter<String, T> getConverter(Class<T> targetType) {
        
        @SuppressWarnings("unchecked")
        Converter<String,T> result = (Converter<String,T>)converterMap.get(targetType);
        if (result == null) {
            result = new StringConverter<>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    /**
     * StringConverter
     */
    private class StringConverter<E extends StringConverterableEnum> implements Converter<String, E> {

        private Map<String, E> enumMap = new HashMap<>();

        public StringConverter(Class<E> enumType) {
            for (E e : enumType.getEnumConstants()) {
                enumMap.put(e.getValue(), e);
            }
        }

        @Override
        public E convert(String source) {
            return enumMap.get(source);
        }
    }
}
