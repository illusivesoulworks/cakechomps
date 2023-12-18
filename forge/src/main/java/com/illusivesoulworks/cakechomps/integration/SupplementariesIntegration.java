package com.illusivesoulworks.cakechomps.integration;

import net.mehvahdjukaar.supplementaries.common.block.blocks.DirectionalCakeBlock;
import net.mehvahdjukaar.supplementaries.common.misc.CakeRegistry;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SupplementariesIntegration {

  public static boolean isDoubleCake(Block block, BlockState blockState, ItemStack itemInHand) {

    if (CommonConfigs.Tweaks.DOUBLE_CAKE_PLACEMENT.get() &&
        CakeRegistry.INSTANCE.getValues().stream()
            .anyMatch(v -> v.cake.asItem() == itemInHand.getItem())) {
      return (CakeRegistry.INSTANCE.getValues().stream().anyMatch(v -> v.cake == block) ||
          block instanceof DirectionalCakeBlock) &&
          blockState.getValue(DirectionalCakeBlock.BITES) == 0;
    }
    return false;
  }
}
