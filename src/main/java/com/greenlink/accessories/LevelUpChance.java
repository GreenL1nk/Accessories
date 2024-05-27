package com.greenlink.accessories;

import org.bukkit.Bukkit;

import java.util.Random;

public enum LevelUpChance {

    ONE(0, 50),
    TWO(1, 40),
    THREE(2, 30),
    FOUR(3, 20),
    FIVE(4, 10),
    SIX(5, 8.5),
    SEVEN(6, 7),
    EIGHT(7, 5),
    NINE(8, 3),
    TEN(9, 2);

    private final int level;
    private final double chance;

    LevelUpChance(int level, double chance) {
        this.level = level;
        this.chance = chance;
    }

    public int getLevel() {
        return level;
    }

    public double getChance() {
        return chance;
    }

    public boolean rollChance() {
        Random random = new Random();
        double roll = random.nextDouble() * 100;
        return roll <= chance;
    }
}
