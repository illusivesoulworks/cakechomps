package com.illusivesoulworks.cakechomps.services;

import com.illusivesoulworks.cakechomps.integration.SupplementariesIntegration;
import com.illusivesoulworks.cakechomps.platform.services.IPlatform;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

public class ForgePlatform implements IPlatform {

  @Override
  public boolean isModLoaded(String id) {
    return ModList.get().isLoaded(id);
  }

  @Override
  public boolean isSupplementariesDoubleCake(Block block, BlockState state, ItemStack itemInHand) {
    return SupplementariesIntegration.isDoubleCake(block, state, itemInHand);
  }
}
