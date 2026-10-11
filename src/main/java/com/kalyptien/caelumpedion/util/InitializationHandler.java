package com.kalyptien.caelumpedion.util;

import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

public class InitializationHandler {
    public static void onServerAboutToStart(ServerAboutToStartEvent event)
    {
        LevelUtils.initializeOnServerStart(event.getServer());
    }

}
