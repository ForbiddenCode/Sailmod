package net.lorent.sailmod.block.masts;

import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class ThickMast extends Mast {
    private int mastSize = 0;

    // Set of positions representing each block that belongs to this Mast
    private final Set<BlockPos> blockPositions = new HashSet<>();

    public ThickMast(int id) {
        super(id);
    }

    public int getMastSize(){
        return mastSize;
    }

    // Add a block to this Mast
    public void addBlock(BlockPos pos) {
            blockPositions.add(pos);
            mastSize++;
    }

    // Remove a block from this Mast
    public void removeBlock(BlockPos pos) {
        blockPositions.remove(pos);
        if(mastSize != 0){
            mastSize--;
        }
    }

    // Get all block positions that belong to this Mast
    public Set<BlockPos> getBlockPositions() {
        return blockPositions;
    }


    // Check if a specific position is part of this Mast
    public boolean containsBlock(BlockPos pos) {
        return blockPositions.contains(pos);
    }

    public int getId() {
        return id;
    }

}
