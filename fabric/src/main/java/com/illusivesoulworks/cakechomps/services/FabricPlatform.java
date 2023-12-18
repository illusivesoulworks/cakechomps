package com.illusivesoulworks.cakechomps.services;

import com.illusivesoulworks.cakechomps.integration.SupplementariesIntegration;
import com.illusivesoulworks.cakechomps.platform.services.IPlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FabricPlatform implements IPlatform {

  @Override
  public boolean isModLoaded(String id) {
    return FabricLoader.getInstance().isModLoaded(id);
  }

  @Override
  public boolean isSupplementariesDoubleCake(Block block, BlockState state, ItemStack itemInHand) {
    return SupplementariesIntegration.isDoubleCake(block, state, itemInHand);
  }
}
