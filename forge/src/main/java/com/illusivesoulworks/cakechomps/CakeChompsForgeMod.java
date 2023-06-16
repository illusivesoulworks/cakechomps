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

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(CakeChompsMod.MOD_ID)
public class CakeChompsForgeMod {

  public CakeChompsForgeMod() {
    ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
        () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY,
            (a, b) -> true));
    MinecraftForge.EVENT_BUS.addListener(this::useCake);
  }

  private void useCake(final PlayerInteractEvent.RightClickBlock evt) {
    Player player = evt.getEntity();
    BlockPos pos = evt.getPos();
    InteractionHand hand = evt.getHand();
    BlockHitResult result = evt.getHitVec();
    CakeChompsMod.useCake(player, pos, hand, () -> {
      UseOnContext useoncontext = new UseOnContext(player, hand, result);
      ItemStack stack = player.getItemInHand(hand);

      if (evt.getUseItem() != Event.Result.DENY) {
        InteractionResult interact = stack.onItemUseFirst(useoncontext);

        if (interact != InteractionResult.PASS) {
          return false;
        }
      }
      boolean flag = !player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty();
      boolean flag1 = (player.isSecondaryUseActive() && flag) &&
          !(player.getMainHandItem().doesSneakBypassUse(player.level(), pos, player) &&
              player.getOffhandItem().doesSneakBypassUse(player.level(), pos, player));
      return evt.getUseBlock() == Event.Result.ALLOW ||
          (evt.getUseBlock() != Event.Result.DENY && !flag1);
    });
  }
}