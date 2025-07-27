package net.adeptstack.ctl;

import net.minecraft.util.StringRepresentable;

import java.util.Arrays;

public enum EBlockPlacePosition implements StringRepresentable {
    TOP_LEFT(0, "topleft"),
    TOP_RIGHT(1, "topright"),
    CENTER(2, "center"),
    BOTTOM_LEFT(3, "bottomleft"),
    BOTTOM_RIGHT(4, "bottomright");

    private String name;
    private int index;

    private EBlockPlacePosition(int index, String name) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.index;
    }

    public static EBlockPlacePosition getSideById(int index) {
        return Arrays.stream(values()).filter(x -> x.getId() == index).findFirst().orElse(CENTER);
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
