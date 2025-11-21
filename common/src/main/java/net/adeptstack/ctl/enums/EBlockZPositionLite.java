package net.adeptstack.ctl.enums;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;

public enum EBlockZPositionLite implements StringRepresentable {
    POSITIVE(1, "positive"),
    NEGATIVE(-1, "negative");

    private String name;
    private int index;

    private EBlockZPositionLite(int index, String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.index;
    }

    public static EBlockZPositionLite getSideById(int index) {
        return Arrays.stream(values()).filter(x -> x.getId() == index).findFirst().orElse(POSITIVE);
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
