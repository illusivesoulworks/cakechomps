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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

@Mod(CakeChomps.MOD_ID)
public class CakeChomps {

  public static final String MOD_ID = "cakechomps";

  private static final Random RANDOM = new Random();

  public CakeChomps() {
    ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
        () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY,
            (a, b) -> true));
    MinecraftForge.EVENT_BUS.addListener(this::interactBlock);
  }

  private void interactBlock(PlayerInteractEvent.RightClickBlock evt) {
    Level world = evt.getWorld();
    Player player = evt.getPlayer();
    BlockPos pos = evt.getPos();
    BlockHitResult rayTraceResult = evt.getHitVec();
    InteractionHand hand = evt.getHand();
    BlockState state = world.getBlockState(pos);
    Block block = state.getBlock();

    if (!(block instanceof CakeBlock) || !player.canEat(false)) {
      return;
    }
    UseOnContext useoncontext = new UseOnContext(player, hand, rayTraceResult);
    ItemStack stack = player.getItemInHand(hand);

    if (evt.getUseItem() != net.minecraftforge.eventbus.api.Event.Result.DENY) {
      InteractionResult result = stack.onItemUseFirst(useoncontext);

      if (result != InteractionResult.PASS) {
        return;
      }
    }
    boolean flag = !player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty();
    boolean flag1 = (player.isSecondaryUseActive() && flag) &&
        !(player.getMainHandItem().doesSneakBypassUse(world, pos, player) &&
            player.getOffhandItem().doesSneakBypassUse(world, pos, player));

    if (evt.getUseBlock() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ||
        (evt.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY && !flag1)) {
      ItemStack blockStack = block.getPickBlock(state, null, world, pos, player);

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

        if (player.level instanceof ServerLevel serverWorld) {
          serverWorld
              .sendParticles(particle, vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z,
                  0.0D);
        } else {
          world.addParticle(particle, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
      }
      player.playSound(player.getEatingSound(blockStack), 0.5F + 0.5F * (float) RANDOM.nextInt(2),
          (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
    }
  }
}
