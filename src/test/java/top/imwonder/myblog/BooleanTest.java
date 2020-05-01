package top.imwonder.myblog;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BooleanTest {
    
    @Test
    public void teatBoolean() {
        Object o = null;
        Boolean b = (Boolean)o;
        log.info("test null ==: {}", b);
    }
}