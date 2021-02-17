package top.imwonder.myblog.enumeration;

import lombok.Getter;

public enum IconType implements StringConverterableEnum {
    BILI("bili"), QQ("qq"), GITHUB("github"), URL("iconUrl"), FILE("file");

    @Getter
    private String value;

    private IconType(String value) {
        this.value = value;
    }

}
