package com.hisnty.lifegame.item;

import com.hisnty.lifegame.component.BillDataComponent;
import com.hisnty.lifegame.player.PlayerData;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BillItem extends Item {

    public BillItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.lifegame.bill"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!level.isClientSide) {
            if(stack.get(BillDataComponent.VALUE) != null){
                int amount = stack.get(BillDataComponent.VALUE);
                PlayerData playerData = new PlayerData();
                playerData.addMoney(player, amount);
                int MONEY = playerData.getMoney(player);
                player.sendSystemMessage(Component.literal("수표 " +stack.get(BillDataComponent.VALUE)+ " 사용, 현재 잔액: " + MONEY));
            }
            stack.shrink(1);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {

        Component translatedName = super.getName(stack);

        // 숫자를 추가하려면 숫자 값을 가져옵니다 (예: stack.getCount() 또는 다른 숫자)
        if(stack.get(BillDataComponent.VALUE) != null) {
            String nameWithNumber = "["+translatedName.getString() + "] " + stack.get(BillDataComponent.VALUE);
            return Component.literal(nameWithNumber);
        }

        return translatedName;
    }
}
