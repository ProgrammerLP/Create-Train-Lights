package net.adeptstack.ctl;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;

public enum EBlockZPosition implements StringRepresentable {
    POSITIVE(1, "positive"),
    CENTER(0, "center"),
    NEGATIVE(-1, "negative");

    private String name;
    private int index;

    private EBlockZPosition(int index, String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.index;
    }

    public static EBlockZPosition getSideById(int index) {
        return Arrays.stream(values()).filter(x -> x.getId() == index).findFirst().orElse(CENTER);
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
