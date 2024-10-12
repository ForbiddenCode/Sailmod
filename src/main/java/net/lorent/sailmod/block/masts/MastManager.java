package net.lorent.sailmod.block.masts;
import net.lorent.sailmod.block.custom.ThickSailMastBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class MastManager {

    // A map that tracks which Mast each BlockPos belongs to
    private final Map<BlockPos, ThickMast> mastMap = new HashMap<>();

    // A map to store all the existing masts by their IDs
    private final Map<Integer, ThickMast> masts = new HashMap<>();

    // A simple counter to assign unique IDs to each Mast
    private int nextMastId = 1;

    // Get the Mast instance for a specific block position
    public ThickMast getMast(BlockPos pos) {
        return mastMap.get(pos);
    }

    public ThickMast getMast(int id){ return masts.get(id);}

    // Create a new Mast instance and add it to the manager
    public ThickMast createMast() {
        ThickMast thickMast = new ThickMast(nextMastId++);
        masts.put(thickMast.getId(), thickMast);
        return thickMast;
    }

    // Add a block to a Mast
    public void addBlockToMast(BlockPos pos, ThickMast mast) {
        mast.addBlock(pos);
        mastMap.put(pos, mast);
    }

    // Remove a block from a Mast
    public void removeBlockFromMast(BlockPos pos) {
        ThickMast mast = mastMap.get(pos);
        if (mast != null) {
            mast.removeBlock(pos);
            mastMap.remove(pos);
        }
    }

    // Check if a block is part of any Mast
    public boolean isPartOfMast(BlockPos pos) {
        return mastMap.containsKey(pos);
    }

    public int getMastIdByPosition(BlockPos pos) {
        ThickMast mast = mastMap.get(pos);
        if (mast != null) {
            return mast.getId();
        }
        return 0;
    }

    public void moveModBlocksToMast(int sourceId, int targetId) {
        ThickMast sourceMast = masts.get(sourceId);
        ThickMast targetMast = masts.get(targetId);
        if (sourceMast == null || targetMast == null) {
            return;
        }
        for (BlockPos blockPos : sourceMast.getBlockPositions()) {
            targetMast.addBlock(blockPos);
            mastMap.put(blockPos, targetMast);
        }
        sourceMast.getBlockPositions().clear();
        if (sourceMast.getBlockPositions().isEmpty()) {
            masts.remove(sourceId);}
    }

    public  void addBlockToMastVisually(Level pLevel, ThickMast mast){
        for (BlockPos pos : mast.getBlockPositions()){
            BlockState currentState = pLevel.getBlockState(pos);
            BlockState newState = currentState.setValue(ThickSailMastBlock.IS_PART_OF_MAST, true);
            pLevel.setBlock(pos, newState, 3);
            pLevel.sendBlockUpdated(pos, newState, newState, 3);
        }
    }

    public  void removeBlockFromMastVisually(BlockPos pos, Level pLevel, ThickMast mast){
            BlockState currentState = pLevel.getBlockState(pos);
            BlockState newState = currentState.setValue(ThickSailMastBlock.IS_PART_OF_MAST, false);
            pLevel.setBlock(pos, newState, 3);
            pLevel.sendBlockUpdated(pos, newState, newState, 3);
    }
    // Additional logic for managing all masts
}
