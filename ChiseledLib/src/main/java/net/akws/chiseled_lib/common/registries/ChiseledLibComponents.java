package net.akws.chiseled_lib.common.registries;

import net.akws.chiseled_lib.common.ChiseledLib;
import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ChiseledLibComponents {

    public static final ComponentType<ItemHighlightComponent> ITEM_HIGHLIGHT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            ChiseledLib.id("item_highlight"),
            ComponentType.<ItemHighlightComponent>builder().codec(ItemHighlightComponent.CODEC).build()
    );

    public static void init() {}
}
