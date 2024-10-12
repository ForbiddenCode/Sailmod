package net.lorent.sailmod.block.custom;

import net.lorent.sailmod.block.masts.MastManager;
import net.lorent.sailmod.block.masts.ThickMast;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThickSailMastBlock extends Block {
    private static final MastManager mastManager = new MastManager();
    public static final IntegerProperty THICKNESS = IntegerProperty.create("thickness", 6, 16);
    public static final BooleanProperty IS_PART_OF_MAST = BooleanProperty.create("is_part_of_mast");
    private static VoxelShape SHAPE = Block.box(0,0,0,16,16,16);

    public ThickSailMastBlock(Properties properties) {
        super(properties);
        // Register the default state of the block with the desired property values
        this.registerDefaultState(this.defaultBlockState()
                .setValue(THICKNESS, 16));
        this.registerDefaultState(this.stateDefinition.any().
                setValue(IS_PART_OF_MAST, false));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos belowPos = pPos.below();
            BlockPos abovePos = pPos.above();
            if(mastManager.isPartOfMast(belowPos) && mastManager.isPartOfMast(abovePos)){
                if(mastManager.getMastIdByPosition(belowPos) != mastManager.getMastIdByPosition(abovePos)){
                    ThickMast existingMast = mastManager.getMast(belowPos);
                    mastManager.addBlockToMast(pPos, existingMast);
                    mastManager.addBlockToMastVisually(pLevel, existingMast);
                    //convert the mast above to the mast below
                    mastManager.moveModBlocksToMast(mastManager.getMastIdByPosition(abovePos),
                            mastManager.getMastIdByPosition(belowPos));
                }
            } else if (mastManager.isPartOfMast(belowPos)) {
                ThickMast existingMast = mastManager.getMast(belowPos);
                mastManager.addBlockToMast(pPos, existingMast);
                mastManager.addBlockToMastVisually(pLevel, existingMast);
            } else if(mastManager.isPartOfMast(abovePos)){
                ThickMast existingMast = mastManager.getMast(abovePos);
                mastManager.addBlockToMast(pPos, existingMast);
                mastManager.addBlockToMastVisually(pLevel, existingMast);
            } else {
                ThickMast newMast = mastManager.createMast();
                mastManager.addBlockToMast(pPos, newMast);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static void setSHAPE(VoxelShape SHAPE, int offset) {
        ThickSailMastBlock.SHAPE = Block.box(0 + offset, 0, 0 + offset, 16 - offset, 16, 16 - offset);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        //super.onRemove(state, world, pos, newState, isMoving);

        if (!world.isClientSide && state.getValue(ThickSailMastBlock.IS_PART_OF_MAST)) { // Only run this logic on the server side
            // Remove the block from its Mast
            ThickMast thickMast = mastManager.getMast(pos);
            if(thickMast != null) {
                mastManager.removeBlockFromMast(pos);
                if(mastManager.isPartOfMast(pos.above()) && pos.above().getY() > pos.getY()){
                    mastManager.removeBlockFromMastVisually(pos.above(), world, thickMast);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_PART_OF_MAST, THICKNESS);
    }


}
