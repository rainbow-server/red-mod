package com.hisnty.lifegame.command;

import com.hisnty.lifegame.component.BillDataComponent;
import com.hisnty.lifegame.item.ModItems;
import com.hisnty.lifegame.player.PlayerData;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class BillProviderCommand {
    public BillProviderCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("수표")
                .then(Commands.argument("amount", IntegerArgumentType.integer()) // 'amount'는 액수 입력 받기
                .executes((command) -> {
                    int amount = IntegerArgumentType.getInteger(command, "amount"); // 액수 얻기
                    return execute(command.getSource(),amount);
                })));
    }

    private int execute(CommandSourceStack source,Integer amount) throws CommandSyntaxException {
        if (source.getEntity() instanceof ServerPlayer player) {
            PlayerData playerData = new PlayerData();
            int MONEY = playerData.getMoney(player);

            if (MONEY >= amount) {
                source.sendSuccess(() -> Component.literal("수표 " + amount + "원이 발급되었습니다. 현재 잔액: " + (MONEY - amount) +"원")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)
                                .withBold(true)                     // 굵게
                                .withItalic(true)), false);
                playerData.addMoney(player, -1*amount);

                ItemStack BILL = new ItemStack(ModItems.BILL_ITEM.get());
                BILL.set(BillDataComponent.VALUE,amount);
                player.addItem(BILL);
            }else{
                source.sendSuccess(() -> Component.literal("잔액이 부족합니다. 현재 잔액: " + MONEY +"원")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);
            }

        } else {
            source.sendFailure(Component.literal("이 명령어는 플레이어만 사용할 수 있습니다!"));
        }
        return Command.SINGLE_SUCCESS;
    }
}
