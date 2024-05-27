package com.greenlink.accessories.accessories;

import javax.annotation.Nullable;
import java.util.Objects;

public final class LevelBuff {
    private final int level;
    private final int cooldown;
    private int duration;
    private final int amplifier;
    private final Integer ticks;

    public LevelBuff(int level, int cooldown, int duration, int amplifier) {
        this.level = level;
        this.cooldown = cooldown;
        this.duration = duration;
        this.amplifier = amplifier;
        ticks = null;
    }

    public LevelBuff(int level, int cooldown, int ticks) {
        this.level = level;
        this.cooldown = cooldown;
        this.ticks = ticks;
        this.amplifier = 0;
    }

    public int level() {
        return level;
    }

    public int cooldown() {
        return cooldown;
    }

    public int duration() {
        return duration;
    }

    public int amplifier() {
        return amplifier;
    }

    @Nullable
    public Integer getTicks() {
        return ticks;
    }
}
