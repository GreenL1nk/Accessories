package com.greenlink.accessories.accessories;

import com.greenlink.accessories.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public record Accessory(PotionEffectType potionEffectType, LevelBuff... levelBuffs) {

    public Accessory(@Nullable PotionEffectType potionEffectType, LevelBuff... levelBuffs) {
        this.levelBuffs = levelBuffs;
        this.potionEffectType = potionEffectType;
    }

    public @Nullable Long applyBuff(Player player, int level) {
        if (potionEffectType != null) {
            LevelBuff buff = getBuffByLevel(level);
            if (buff == null) return null;
            if (buff.getTicks() != null)
                player.addPotionEffect(new PotionEffect(potionEffectType, buff.getTicks(), buff.amplifier()));
            else player.addPotionEffect(new PotionEffect(potionEffectType, buff.duration() * 20, buff.amplifier()));
            return TimeUtils.secondsToMilliseconds(buff.cooldown());
        }
        return null;
    }

    public @Nullable LevelBuff getBuffByLevel(int level) {
        LevelBuff buff = Arrays.stream(levelBuffs).filter(levelBuff -> levelBuff.level() == level).findFirst().orElse(null);
        if (buff == null) {
            buff = Arrays.stream(levelBuffs).filter(levelBuff -> levelBuff.level() < level).max(Comparator.comparingInt(LevelBuff::level)).orElse(null);
        }
        return buff;
    }
}
