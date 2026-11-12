package net.akws.chiseled_lib.common.registries;

import com.mojang.serialization.Codec;
import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public interface ChiseledLibComponents {

    DataComponentType<ItemHighlightComponent> ITEM_HIGHLIGHT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ChiseledLib.id("item_highlight"),
            DataComponentType.<ItemHighlightComponent>builder().persistent(ItemHighlightComponent.CODEC).ignoreSwapAnimation().build()
    );
    DataComponentType<Boolean> EMISSIVE_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ChiseledLib.id("emissive_component"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
    );

    static void init() {}
}
