package top.imwonder.myblog.experiment;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionTest {
    
    @Test
    public void reflectionTest() throws NoSuchMethodException, SecurityException {
        log.info("int==={}",ReflectionTest.class.getMethod("testInt", int.class).getParameterTypes()[0].getName());
        char i = 'i';
        log.info("i-32===={}===int=={}", (char)(i-32),(int)i);
    }

    public static void testInt(int a){}
}