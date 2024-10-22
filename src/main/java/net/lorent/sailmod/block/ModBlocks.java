package net.lorent.sailmod.block;

import net.lorent.sailmod.SailMod;
import net.lorent.sailmod.block.custom.ThickSailMastBlock;
import net.lorent.sailmod.block.custom.SailYardBlock;
import net.lorent.sailmod.item.ModCreativeModeTab;
import net.lorent.sailmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public  static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SailMod.MOD_ID);


    public static final RegistryObject<Block> OAK_SAIL_MAST_BLOCK =
            registerBlock("thick_sail_mast_block", () -> new ThickSailMastBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion()),
                    ModCreativeModeTab.TUTORIAL_TAB);
    public static final RegistryObject<Block> SAIL_YARD_BLOCK =
            registerBlock("sail_yard_block", () -> new SailYardBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion()),
                    ModCreativeModeTab.TUTORIAL_TAB);


    private static <T extends Block> RegistryObject<T>
    registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item>
    registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
