package com.hisnty.lifegame.player;

import net.minecraft.world.entity.player.Player;

import static com.hisnty.lifegame.player.PlayerDataAttatchment.MONEY;

public class PlayerData implements IPlayerData{

    @Override
    public int getMoney(Player player) {
        int money = player.getData(MONEY);
        return money;
    }

    @Override
    public void addMoney(Player player, int amount) {
        player.setData(MONEY, player.getData(MONEY) + amount);
    }

    @Override
    public void setMoney(Player player, int amount) {
        player.setData(MONEY, amount);
    }
}
