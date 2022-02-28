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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ChunkLoader.MOD_ID, name = ChunkLoader.MOD_NAME, version = ChunkLoader.VERSION)
public class ChunkLoader {
    public static final Logger logger = LogManager.getLogger("ChunkLoader");

    public static final String MOD_ID = "chunkloader";
    public static final String MOD_NAME = "ChunkLoader";
    public static final String VERSION = "1.0.0";

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
        ChunkProviderClient chunkProviderClient = (ChunkProviderClient) world.getChunkProvider();

        int WORLD_SIZE = 100000 >> 4;
        for (int i = 0; i < 4 * WORLD_SIZE * WORLD_SIZE; i++) {
            int chunkX = i % (2 * WORLD_SIZE) - WORLD_SIZE;
            int chunkZ = i / (2 * WORLD_SIZE) - WORLD_SIZE;
            chunkProviderClient.loadChunk(chunkX, chunkZ);
        }
    }
}
