package net.lorent.sailmod.item;

import net.lorent.sailmod.SailMod;
import net.lorent.sailmod.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;


public class ModCreativeModeTab {
    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab("tutorialtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.OAK_SAIL_MAST_BLOCK.get());
        }
    };
}
