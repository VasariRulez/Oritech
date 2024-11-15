package rearth.oritech.item.tools.harvesting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rearth.oritech.Oritech;
import rearth.oritech.item.tools.util.OritechEnergyItem;

import java.util.List;

public class DrillItem extends MiningToolItem implements OritechEnergyItem {
    
    public static final int BAR_STEP_COUNT = 13;
    private final float energyUsageMultiplier = Oritech.CONFIG.basicDrill.energyUsage();
    
    public DrillItem(ToolMaterial toolMaterial, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(toolMaterial, effectiveBlocks, settings);
    }
    
    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        
        if (!(miner instanceof PlayerEntity player)) return true;
        
        var amount = state.getBlock().getHardness() * energyUsageMultiplier;
        amount = Math.min(amount, this.getStoredEnergy(stack));
        
        return this.tryUseEnergy(stack, (long) amount, player);
    }
    
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var text = Text.translatable("tooltip.oritech.energy_indicator", this.getStoredEnergy(stack), this.getEnergyCapacity(stack));
        tooltip.add(text.formatted(Formatting.GOLD));
    }
    
    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        var enoughEnergy = getStoredEnergy(stack) >= state.getBlock().getHardness() * energyUsageMultiplier;
        var multiplier = enoughEnergy ? 1 : 0.1f;
        return super.getMiningSpeed(stack, state) * multiplier;
    }
    
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
    
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    
    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((getStoredEnergy(stack) * 100f / this.getEnergyCapacity(stack)) * BAR_STEP_COUNT) / 100;
    }
    
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
    
    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xff7007;
    }
    
    @Override
    public long getEnergyCapacity(ItemStack stack) {
        return Oritech.CONFIG.basicDrill.energyCapacity();
    }
    
    @Override
    public long getEnergyMaxInput(ItemStack stack) {
        return Oritech.CONFIG.basicDrill.chargeSpeed();
    }
}