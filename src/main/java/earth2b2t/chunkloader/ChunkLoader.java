package earth2b2t.chunkloader;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = ChunkLoader.MOD_ID, name = ChunkLoader.MOD_NAME, version = ChunkLoader.VERSION)
public class ChunkLoader {
    public static final Logger logger = LogManager.getLogger("ChunkLoader");

    public static final String MOD_ID = "chunkloader";
    public static final String MOD_NAME = "ChunkLoader";
    public static final String VERSION = "1.0.0";

    private final int WORLD_SIZE = 100000 >> 4;
    private ChunkProviderClient chunkProviderClient;
    private int index;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Initializing ChunkLoader...");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerSP)) return;

        World world = event.getWorld();
        if (!(world.getChunkProvider() instanceof ChunkProviderClient)) return;
        chunkProviderClient = (ChunkProviderClient) world.getChunkProvider();
        index = 0;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (chunkProviderClient == null) return;
        if (index > 4 * WORLD_SIZE * WORLD_SIZE) return;
        for (int j = 0; j < 100; j++) { // 1 tick 50 chunk
            int chunkX = index % (2 * WORLD_SIZE) - WORLD_SIZE;
            int chunkZ = index / (2 * WORLD_SIZE) - WORLD_SIZE;
            chunkProviderClient.loadChunk(chunkX, chunkZ);
            index++;
        }
    }
}
