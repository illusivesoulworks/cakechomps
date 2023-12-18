package com.illusivesoulworks.cakechomps.platform.services;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IPlatform {

  boolean isModLoaded(String id);

  boolean isSupplementariesDoubleCake(Block block, BlockState state, ItemStack itemInHand);
}
