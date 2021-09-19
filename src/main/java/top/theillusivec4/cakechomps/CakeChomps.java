/*
 * Copyright (C) 2018-2020  C4
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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(CakeChomps.MODID)
public class CakeChomps {

  public static final String MODID = "cakechomps";

  private static final Random RAND = new Random();

  public CakeChomps() {
    ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST,
        () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    MinecraftForge.EVENT_BUS.addListener(this::interactBlock);
  }

  private void interactBlock(PlayerInteractEvent.RightClickBlock evt) {
    World world = evt.getWorld();
    PlayerEntity player = evt.getPlayer();
    BlockPos pos = evt.getPos();
    BlockRayTraceResult rayTraceResult = evt.getHitVec();
    Hand hand = evt.getHand();
    BlockState state = world.getBlockState(pos);
    Block block = state.getBlock();

    if (!(block instanceof CakeBlock) || !player.canEat(false)) {
      return;
    }
    ItemUseContext itemusecontext = new ItemUseContext(player, hand, rayTraceResult);
    ItemStack stack = player.getHeldItem(hand);

    if (evt.getUseItem() != net.minecraftforge.eventbus.api.Event.Result.DENY) {
      ActionResultType result = stack.onItemUseFirst(itemusecontext);

      if (result != ActionResultType.PASS) {
        return;
      }
    }
    boolean flag =
        !player.getHeldItemMainhand().isEmpty() || !player.getHeldItemOffhand().isEmpty();
    boolean flag1 = (player.isSecondaryUseActive() && flag) &&
        !(player.getHeldItemMainhand().doesSneakBypassUse(world, pos, player) &&
            player.getHeldItemOffhand().doesSneakBypassUse(world, pos, player));

    if (evt.getUseBlock() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ||
        (evt.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY && !flag1)) {
      ItemStack blockStack = block.getPickBlock(state, null, world, pos, player);

      for (int i = 0; i < 16; ++i) {
        Vector3d vec3d = new Vector3d(((double) RAND.nextFloat() - 0.5D) * 0.1D,
            Math.random() * 0.1D + 0.1D, 0.0D);
        vec3d = vec3d.rotatePitch(-player.rotationPitch * ((float) Math.PI / 180F));
        vec3d = vec3d.rotateYaw(-player.rotationYaw * ((float) Math.PI / 180F));
        double d0 = (double) (-RAND.nextFloat()) * 0.6D - 0.3D;
        Vector3d vec3d1 = new Vector3d(((double) RAND.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
        vec3d1 = vec3d1.rotatePitch(-player.rotationPitch * ((float) Math.PI / 180F));
        vec3d1 = vec3d1.rotateYaw(-player.rotationYaw * ((float) Math.PI / 180F));
        vec3d1 = vec3d1.add(player.getPosX(), player.getPosYEye(), player.getPosZ());
        ItemParticleData particle = new ItemParticleData(ParticleTypes.ITEM, blockStack);

        if (player.world instanceof ServerWorld) {
          ServerWorld serverWorld = (ServerWorld) player.world;
          serverWorld.spawnParticle(particle, vec3d1.x, vec3d1.y, vec3d1.z, 1, vec3d.x,
              vec3d.y + 0.05D, vec3d.z, 0.0D);
        } else {
          world.addParticle(particle, vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D,
              vec3d.z);
        }
      }
      player.playSound(player.getEatSound(blockStack), 0.5F + 0.5F * (float) RAND.nextInt(2),
          (RAND.nextFloat() - RAND.nextFloat()) * 0.2F + 1.0F);
    }
  }
}
