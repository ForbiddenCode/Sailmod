package net.lorent.sailmod.block.masts;

import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public abstract class Mast {
    protected int id;
    protected Set<BlockPos> blockPositions = new HashSet<>();

    public Mast(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<BlockPos> getBlockPositions() {
        return blockPositions;
    }

    public void addBlockPosition(BlockPos pos) {
        blockPositions.add(pos);
    }
}