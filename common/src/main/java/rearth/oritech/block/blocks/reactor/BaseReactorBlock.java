package rearth.oritech.block.blocks.reactor;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public abstract class BaseReactorBlock extends Block {
    
    public BaseReactorBlock(Settings settings) {
        super(settings);
    }
    
    public boolean validForWalls() { return false; }
    
    public Block requiredStackCeiling() {
        return Blocks.AIR;
    }
    
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        
        var showExtra = Screen.hasControlDown();
        
        if (showExtra) {
            var machineId = Registries.ITEM.getId(stack.getItem()).getPath();
            tooltip.add(Text.translatable("tooltip.oritech." + machineId));
            
            for (int i = 0; i < 6; i++) {
                var key = "tooltip.oritech." + machineId + "." + i;
                if (I18n.hasTranslation(key)) {
                    tooltip.add(Text.translatable(key).formatted(Formatting.GRAY, Formatting.ITALIC));
                }
            }
        } else {
            tooltip.add(Text.translatable("tooltip.oritech.item_extra_info").formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
        }
        
    }
}
