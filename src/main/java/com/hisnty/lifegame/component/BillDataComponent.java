package com.hisnty.lifegame.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import static com.hisnty.lifegame.ExampleMod.MODID;
import static com.mojang.serialization.Codec.INT;


public class BillDataComponent {

    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> VALUE =
            register("value", builder -> builder.persistent(Codec.INT));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
