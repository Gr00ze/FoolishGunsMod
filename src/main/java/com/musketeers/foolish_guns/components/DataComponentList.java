package com.musketeers.foolish_guns.components;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;

import static com.musketeers.foolish_guns.utils.RegistrationUtils.registerComponent;

public class DataComponentList {
    public static final DataComponentType<Boolean> IS_LOADING = registerComponent("is_loading", DataComponentType.<Boolean>builder().persistent(Codec.BOOL));

    public static void initialize(){}
}
