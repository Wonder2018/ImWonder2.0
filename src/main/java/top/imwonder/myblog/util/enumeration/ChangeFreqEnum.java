package top.imwonder.myblog.util.enumeration;

import lombok.Getter;

public enum ChangeFreqEnum {
    ALWAYS("always"), HOURLY("hourly"), DAILY("daily"), WEEKLY("weekly"), MONTHLY("monthly"), YEARLY("yearly"),
    NEVER("never");

    @Getter
    private String name;

    ChangeFreqEnum(String name) {
        this.name = name;
    }

}