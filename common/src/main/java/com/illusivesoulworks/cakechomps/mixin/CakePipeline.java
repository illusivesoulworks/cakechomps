package com.illusivesoulworks.cakechomps.mixin;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CakePipeline {

  private static final Random RANDOM = new Random();

  private int lastBiteLevel = -1;
  private ItemStack refStack = ItemStack.EMPTY;

  public void init(Level level, BlockHitResult blockHitResult) {
    BlockPos pos = blockHitResult.getBlockPos();
    BlockState state = level.getBlockState(pos);
    state.getOptionalValue(CakeBlock.BITES).ifPresent(val -> {
      this.lastBiteLevel = val;
      this.refStack = state.getBlock().getCloneItemStack(level, pos, state);
    });
  }

  public void runCheck(Player player, Level level, BlockHitResult blockHitResult) {

    if (this.lastBiteLevel >= 0) {
      BlockPos pos = blockHitResult.getBlockPos();
      BlockState state = level.getBlockState(pos);
      boolean flag =
          state.getOptionalValue(CakeBlock.BITES).map(val -> val > this.lastBiteLevel).orElse(true);

      if (flag) {
        ItemStack blockStack = this.refStack;

        if (!blockStack.isEmpty()) {
          Consumable consumable =
              blockStack.getOrDefault(DataComponents.CONSUMABLE, Consumables.DEFAULT_FOOD);

          if (consumable.shouldEmitParticlesAndSounds(0)) {
            consumable.emitParticlesAndSounds(player.getRandom(), player, blockStack, 5);
          }
        }
      }
    }
    this.lastBiteLevel = -1;
  }
}
