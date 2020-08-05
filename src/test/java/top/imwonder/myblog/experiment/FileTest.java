package top.imwonder.myblog.experiment;

import java.io.File;

import org.junit.jupiter.api.Test;


public class FileTest {
    @Test
    public void pcpath() {
        File parent = new File("target");
        File child = new File(parent, "aa/bb/bb");
        child.getParentFile().mkdirs();
    }
}