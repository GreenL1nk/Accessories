package com.greenlink.accessories.utils;

public enum EffectKeys {
    SPEED("speed"),

    /**
     * Decreases movement speed.
     */
    SLOW("slowness"),

    /**
     * Increases dig speed.
     */
    FAST_DIGGING("haste"),

    /**
     * Decreases dig speed.
     */
    SLOW_DIGGING("mining_fatigue"),

    /**
     * Increases damage dealt.
     */
    INCREASE_DAMAGE("strength"),

    /**
     * Heals an entity.
     */
    HEAL("instant_health"),

    /**
     * Hurts an entity.
     */
    HARM("instant_damage"),

    /**
     * Increases jump height.
     */
    JUMP("jump_boost"),

    /**
     * Warps vision on the client.
     */
    CONFUSION("nausea"),

    /**
     * Regenerates health.
     */
    REGENERATION("regeneration"),

    /**
     * Decreases damage dealt to an entity.
     */
    DAMAGE_RESISTANCE("resistance"),

    /**
     * Stops fire damage.
     */
    FIRE_RESISTANCE("fire_resistance"),

    /**
     * Allows breathing underwater.
     */
    WATER_BREATHING("water_breathing"),

    /**
     * Grants invisibility.
     */
    INVISIBILITY("invisibility"),

    /**
     * Blinds an entity.
     */
    BLINDNESS("blindness"),

    /**
     * Allows an entity to see in the dark.
     */
    NIGHT_VISION("night_vision"),

    /**
     * Increases hunger.
     */
    HUNGER("hunger"),

    /**
     * Decreases damage dealt by an entity.
     */
    WEAKNESS("weakness"),

    /**
     * Deals damage to an entity over time.
     */
    POISON("poison"),

    /**
     * Deals damage to an entity over time and gives the health to the
     * shooter.
     */
    WITHER("wither"),

    /**
     * Increases the maximum health of an entity.
     */
    HEALTH_BOOST("health_boost"),

    /**
     * Increases the maximum health of an entity with health that cannot be
     * regenerated, but is refilled every 30 seconds.
     */
    ABSORPTION("absorption"),

    /**
     * Increases the food level of an entity each tick.
     */
    SATURATION("saturation"),

    /**
     * Outlines the entity so that it can be seen from afar.
     */
    GLOWING("glowing"),

    /**
     * Causes the entity to float into the air.
     */
    LEVITATION("levitation"),

    /**
     * Loot table luck.
     */
    LUCK("luck"),

    /**
     * Loot table unluck.
     */
    UNLUCK("unluck"),

    /**
     * Slows entity fall rate.
     */
    SLOW_FALLING("slow_falling"),

    /**
     * Effects granted by a nearby conduit. Includes enhanced underwater abilities.
     */
    CONDUIT_POWER("conduit_power"),

    /**
     * Increses underwater movement speed.<br>
     * Squee'ek uh'k kk'kkkk squeek eee'eek.
     */
    DOLPHINS_GRACE("dolphins_grace"),

    /**
     * Triggers a raid when the player enters a village.<br>
     * oof.
     */
    BAD_OMEN("bad_omen"),

    /**
     * Reduces the cost of villager trades.<br>
     * \o/.
     */
    HERO_OF_THE_VILLAGE("hero_of_the_village"),

    /**
     * Causes the player's vision to dim occasionally.
     */
    DARKNESS("darkness");

    private final String key;
    EffectKeys(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
