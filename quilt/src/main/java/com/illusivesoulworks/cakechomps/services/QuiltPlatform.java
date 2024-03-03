package com.illusivesoulworks.cakechomps.services;

import com.illusivesoulworks.cakechomps.integration.AmendmentsIntegration;
import com.illusivesoulworks.cakechomps.platform.services.IPlatform;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.quiltmc.loader.api.QuiltLoader;

public class QuiltPlatform implements IPlatform {

  @Override
  public boolean isModLoaded(String id) {
    return QuiltLoader.isModLoaded(id);
  }

  @Override
  public boolean isAmendmentsDoubleCake(Block block, BlockState state, ItemStack itemInHand) {
    return AmendmentsIntegration.isDoubleCake(block, state, itemInHand);
  }
}
