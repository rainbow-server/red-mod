package com.hisnty.lifegame;

import com.hisnty.lifegame.command.BillProviderCommand;
import com.hisnty.lifegame.command.MyMoneyCommand;
import com.hisnty.lifegame.component.BillDataComponent;
import com.hisnty.lifegame.item.ModItems;
import com.hisnty.lifegame.player.PlayerDataAttatchment;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;


import net.minecraft.world.item.CreativeModeTabs;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.sql.Connection;

@Mod(ExampleMod.MODID)
public class ExampleMod
{
    public static final String MODID = "lifegame";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        ModItems.register(modEventBus);
        PlayerDataAttatchment.register(modEventBus);
        BillDataComponent.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.BILL_ITEM);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            new MyMoneyCommand(dispatcher);
            new BillProviderCommand(dispatcher);
            LOGGER.info("[MyMod] SUCCESS: Commands registered");
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
