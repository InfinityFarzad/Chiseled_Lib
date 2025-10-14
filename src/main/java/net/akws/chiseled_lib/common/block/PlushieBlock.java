package net.akws.chiseled_lib.common.block;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Map;

public class PlushieBlock extends Block {
    public static final MapCodec<PlushieBlock> CODEC = createCodec(PlushieBlock::new);
    public static final EnumProperty<Direction> FACING;
    public static Map<Direction, VoxelShape> SHAPES_BY_DIRECTION;
    public PlushieBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING,Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_DIRECTION.get(state.get(FACING));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public MapCodec<PlushieBlock> getCodec() {
        return CODEC;
    };

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
        SHAPES_BY_DIRECTION = VoxelShapes.createHorizontalFacingShapeMap(Block.createColumnShape(12,6,0,13));
    }
}
