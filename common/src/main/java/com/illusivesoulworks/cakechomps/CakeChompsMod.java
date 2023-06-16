/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Cake Chomps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Cake Chomps is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Cake Chomps.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.cakechomps;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CakeChompsMod {

  public static final String MOD_ID = "cakechomps";
  public static final String MOD_NAME = "Cake Chomps";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

  private static final Random RANDOM = new Random();

  public static void useCake(Player player, BlockPos pos, InteractionHand hand,
                             Supplier<Boolean> canInteract) {
    Level level = player.level();
    BlockState state = level.getBlockState(pos);
    Block block = state.getBlock();

    if (!player.canEat(false)) {
      return;
    }

    if (!(block instanceof CandleCakeBlock) && !(block instanceof CakeBlock)) {
      return;
    }

    if (player.getItemInHand(hand).is(ItemTags.CANDLES) && block instanceof CakeBlock &&
        state.getOptionalValue(CakeBlock.BITES).map(val -> val == 0).orElse(false)) {
      return;
    }

    if (canInteract.get()) {
      ItemStack blockStack = block.getCloneItemStack(level, pos, state);

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
          serverWorld.sendParticles(particle, vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D,
              vec3.z, 0.0D);
        } else {
          level.addParticle(particle, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
      }
      player.playSound(player.getEatingSound(blockStack), 0.5F + 0.5F * (float) RANDOM.nextInt(2),
          (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
    }
  }
}