package com.illusivesoulworks.cakechomps.integration;

import net.mehvahdjukaar.amendments.common.CakeRegistry;
import net.mehvahdjukaar.amendments.common.block.DirectionalCakeBlock;
import net.mehvahdjukaar.amendments.configs.CommonConfigs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AmendmentsIntegration {

  public static boolean isDoubleCake(Block block, BlockState blockState, ItemStack itemInHand) {

    if (CommonConfigs.DOUBLE_CAKES.get() &&
        CakeRegistry.INSTANCE.getValues().stream()
            .anyMatch(v -> v.cake.asItem() == itemInHand.getItem())) {
      return (CakeRegistry.INSTANCE.getValues().stream().anyMatch(v -> v.cake == block) ||
          block instanceof DirectionalCakeBlock) &&
          blockState.getValue(DirectionalCakeBlock.BITES) == 0;
    }
    return false;
  }
}
