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

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class CakeChomps implements ModInitializer {

    private static final Random RAND = new Random();

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            Block block = world.getBlockState(pos).getBlock();

            if (block instanceof CakeBlock && player.canConsume(false)) {
                Item item = Item.BLOCK_ITEM_MAP.get(block);
                ItemStack stack = new ItemStack(item);

                for (int i = 0; i < 5; i++) {
                    Vec3d vec3d = new Vec3d(((double)RAND.nextFloat() - 0.5D) * 0.1D, RAND.nextDouble() * 0.1D + 0.1D, 0.0D);
                    vec3d = vec3d.rotateX(-player.pitch * 0.017453292F);
                    vec3d = vec3d.rotateY(-player.yaw * 0.017453292F);
                    double d0 = (double)(-RAND.nextFloat()) * 0.6D - 0.3D;
                    Vec3d vec3d1 = new Vec3d(((double)RAND.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
                    vec3d1 = vec3d1.rotateX(-player.pitch * 0.017453292F);
                    vec3d1 = vec3d1.rotateY(-player.yaw * 0.017453292F);
                    vec3d1 = vec3d1.add(player.x, player.y + (double)player.getStandingEyeHeight(), player.z);
                    world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), vec3d1.x, vec3d1.y, vec3d1.z,
                            vec3d.x, vec3d.y + 0.05D, vec3d.z);
                }
                world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 0.5F + 0.5F *
                        (float)RAND.nextInt(2), (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1F);
            }
            return ActionResult.PASS;
        });
    }
}
