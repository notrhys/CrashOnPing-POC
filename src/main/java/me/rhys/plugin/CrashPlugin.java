package me.rhys.plugin;

import com.github.retrooper.packetevents.PacketEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class CrashPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        PacketEvents.getAPI().getEventManager().registerListener(new PacketHook());
    }
}
