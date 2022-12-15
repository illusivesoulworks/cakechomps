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

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class CakeChompsQuiltMod implements ModInitializer {

  @Override
  public void onInitialize(ModContainer modContainer) {
    UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
      CakeChompsMod.useCake(player, hitResult.getBlockPos(), hand, () -> {
        boolean bl = !player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty();
        return !(player.isSecondaryUseActive() && bl);
      });
      return InteractionResult.PASS;
    });
  }
}
