package com.illusivesoulworks.cakechomps.mixin;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

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

        for (int i = 0; i < 16; ++i) {
          Vec3 vec3 =
              new Vec3(((double) RANDOM.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D,
                  0.0D);
          vec3 = vec3.xRot(-player.getXRot() * ((float) Math.PI / 180F));
          vec3 = vec3.yRot(-player.getYRot() * ((float) Math.PI / 180F));
          double d0 = (double) (-RANDOM.nextFloat()) * 0.6D - 0.3D;
          Vec3 vec31 = new Vec3(((double) RANDOM.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
          vec31 = vec31.xRot(-player.getXRot() * ((float) Math.PI / 180F));
          vec31 = vec31.yRot(-player.getYRot() * ((float) Math.PI / 180F));
          vec31 = vec31.add(player.getX(), player.getEyeY(), player.getZ());
          ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, blockStack);

          if (player.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(particle, vec31.x, vec31.y, vec31.z, 1, vec3.x,
                vec3.y + 0.05D, vec3.z, 0.0D);
          } else {
            level.addParticle(particle, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
          }
        }
        player.playSound(player.getEatingSound(blockStack), 0.5F + 0.5F * (float) RANDOM.nextInt(2),
            (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
      }
    }
    this.lastBiteLevel = -1;
  }
}
