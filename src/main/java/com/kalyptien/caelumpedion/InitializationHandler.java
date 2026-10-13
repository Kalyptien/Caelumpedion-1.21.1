package com.kalyptien.caelumpedion;

import com.kalyptien.caelumpedion.util.VoronoiLevel;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

public class InitializationHandler {
    public static void onServerAboutToStart(ServerAboutToStartEvent event)
    {
        VoronoiLevel.initializeOnServerStart(event.getServer());
    }

}
