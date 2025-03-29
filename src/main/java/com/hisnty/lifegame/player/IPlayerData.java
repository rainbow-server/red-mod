package com.hisnty.lifegame.player;

import net.minecraft.world.entity.player.Player;

public interface IPlayerData {
    int getMoney(Player player);
    void addMoney(Player player, int amount);
    void setMoney(Player player, int amount);
}
