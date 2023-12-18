package com.illusivesoulworks.cakechomps.platform;

import com.illusivesoulworks.cakechomps.CakeChompsMod;
import com.illusivesoulworks.cakechomps.platform.services.IPlatform;
import java.util.ServiceLoader;

public class Services {

  public static final IPlatform PLATFORM = load(IPlatform.class);

  public static <T> T load(Class<T> clazz) {
    final T loadedService = ServiceLoader.load(clazz)
        .findFirst()
        .orElseThrow(
            () -> new NullPointerException("Failed to load service for " + clazz.getName()));
    CakeChompsMod.LOG.debug("Loaded {} for service {}", loadedService, clazz);
    return loadedService;
  }
}
