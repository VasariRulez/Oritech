package rearth.oritech.block.blocks.reactor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ReactorRedstonePortBlock extends BaseReactorBlock {
    
    public static final IntProperty PORT_MODE = IntProperty.of("port_mode", 0, 2);  // 0 = temperature, 1 = fuel, 2 = power
    
    public ReactorRedstonePortBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(Properties.FACING, Direction.NORTH).with(PORT_MODE, 0).with(Properties.POWER, 0));
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING);
        builder.add(PORT_MODE);
        builder.add(Properties.POWER);
    }
    
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        
        if (world.isClient) return ActionResult.SUCCESS;
        
        var lastMode = state.get(PORT_MODE);
        var cycledMode = (lastMode + 1) % 3;
        
        player.sendMessage(Text.translatable("tooltip.oritech.reactor_port_mode." + cycledMode));
        
        var newState = state.with(PORT_MODE, cycledMode);
        world.setBlockState(pos, newState);
        
        return ActionResult.SUCCESS;
        
    }
    
    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }
    
    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(Properties.POWER);
    }
    
    @Override
    public boolean validForWalls() {
        return true;
    }
}
