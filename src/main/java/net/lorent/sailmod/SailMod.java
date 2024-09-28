package net.lorent.sailmod;

import net.lorent.sailmod.block.ModBlocks;
import net.lorent.sailmod.item.ModItems;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SailMod.MOD_ID)
public class SailMod {
    public static final String MOD_ID = "sailmod";

    private static final Logger LOGGER = LogManager.getLogger();

    public SailMod(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("TEST");
        LOGGER.info("Block >> {}", Blocks.DIRT.getRegistryName());
    }
}
