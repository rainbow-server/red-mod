package com.hisnty.lifegame.command;

import com.hisnty.lifegame.player.PlayerData;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class MyMoneyCommand {

    public MyMoneyCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("돈").executes((command) -> {
            return execute(command.getSource());
        }));
    }

    private int execute(CommandSourceStack source) throws CommandSyntaxException {
        if (source.getEntity() instanceof ServerPlayer player) {
            String playerName = player.getName().getString();
            Integer MONEY = new PlayerData().getMoney(player);
            if (MONEY != null) {
                source.sendSuccess(() -> Component.literal(playerName + "님의 돈: " + MONEY + "원")
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)    // 빨간색
                                .withBold(true)                     // 굵게
                                .withItalic(true)), false);
            }

        } else {
            source.sendFailure(Component.literal("이 명령어는 플레이어만 사용할 수 있습니다!"));
        }
        return Command.SINGLE_SUCCESS;
    }
}
