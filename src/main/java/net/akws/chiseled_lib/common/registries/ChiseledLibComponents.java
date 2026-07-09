package net.akws.chiseled_lib.common.registries;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ChiseledLibComponents {

    public static final DataComponentType<ItemHighlightComponent> ITEM_HIGHLIGHT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ChiseledLib.id("item_highlight"),
            DataComponentType.<ItemHighlightComponent>builder().persistent(ItemHighlightComponent.CODEC).build()
    );

    public static void init() {}
}
