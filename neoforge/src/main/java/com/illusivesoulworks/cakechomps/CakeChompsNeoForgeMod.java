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

import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

@Mod(CakeChompsMod.MOD_ID)
public class CakeChompsNeoForgeMod {

  public CakeChompsNeoForgeMod() {
    ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
        () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY,
            (a, b) -> true));
  }
}