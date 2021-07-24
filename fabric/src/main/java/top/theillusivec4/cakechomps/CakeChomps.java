/*
 * Copyright (C) 2018-2019  C4
 *
 * This file is part of Cake Chomps, a mod made for Minecraft.
 *
 * Cake Chomps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cake Chomps is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Cake Chomps.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.cakechomps;

import java.util.Random;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CakeChomps implements ModInitializer {

  private static final Random RANDOM = new Random();

  @Override
  public void onInitialize() {
    UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
      BlockPos pos = hitResult.getBlockPos();
      Block block = world.getBlockState(pos).getBlock();

      if (block instanceof CakeBlock && player.canConsume(false)) {
        Item item = Item.BLOCK_ITEMS.get(block);

        if (item != null) {
          ItemStack stack = new ItemStack(item);
          float modifier = 0.017453292F;

          for (int i = 0; i < 16; i++) {
            Vec3d vec3d = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.1D,
                RANDOM.nextDouble() * 0.1D + 0.1D, 0.0D);
            vec3d = vec3d.rotateX(-player.getPitch() * modifier);
            vec3d = vec3d.rotateY(-player.getYaw() * modifier);
            double d0 = (double) (-RANDOM.nextFloat()) * 0.6D - 0.3D;
            Vec3d vec3d1 = new Vec3d(((double) RANDOM.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec3d1 = vec3d1.rotateX(-player.getPitch() * modifier);
            vec3d1 = vec3d1.rotateY(-player.getYaw() * modifier);
            vec3d1 = vec3d1.add(player.getX(), player.getEyeY(), player.getZ());
            world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), vec3d1.x,
                vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z);
          }
          player.playSound(player.getEatSound(stack), 0.5F + 0.5F * (float) RANDOM.nextInt(2),
              (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1F);
        }
      }
      return ActionResult.PASS;
    });
  }
}
