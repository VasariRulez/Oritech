package rearth.oritech.init.world.features.uranium;

import io.wispforest.endec.Endec;
import io.wispforest.endec.impl.StructEndecBuilder;
import io.wispforest.owo.serialization.endec.MinecraftEndecs;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public record UraniumPatchFeatureConfig(int number, Identifier blockId) implements FeatureConfig {
    
    public static final Endec<UraniumPatchFeatureConfig> URANIUM_FEATURE_ENDEC = StructEndecBuilder.of(
      Endec.INT.fieldOf("number", UraniumPatchFeatureConfig::number),
      MinecraftEndecs.IDENTIFIER.fieldOf("blockId", UraniumPatchFeatureConfig::blockId),
      UraniumPatchFeatureConfig::new
    );
    
}
