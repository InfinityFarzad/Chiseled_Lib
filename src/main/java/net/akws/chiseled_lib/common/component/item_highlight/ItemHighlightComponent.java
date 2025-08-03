package net.akws.chiseled_lib.common.component.item_highlight;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ItemHighlightComponent(int color, boolean show) {
    public static final Codec<ItemHighlightComponent> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.INT.fieldOf("color").forGetter(ItemHighlightComponent::color),
                Codec.BOOL.optionalFieldOf("show", false).forGetter(ItemHighlightComponent::show)
        ).apply(builder, ItemHighlightComponent::new);
    });


}
