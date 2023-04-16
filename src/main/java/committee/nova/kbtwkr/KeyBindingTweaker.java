package committee.nova.kbtwkr;

import committee.nova.kbtwkr.capabilities.api.IKeyBindState;
import committee.nova.kbtwkr.proxy.CommonProxy;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = KeyBindingTweaker.MODID, useMetadata = true)
public class KeyBindingTweaker {
    public static final String MODID = "kbtwkr";
    private static Logger logger;

    @CapabilityInject(IKeyBindState.class)
    public static Capability<IKeyBindState> kbtwkrCap = null;

    @SidedProxy(serverSide = "committee.nova.kbtwkr.proxy.CommonProxy", clientSide = "committee.nova.kbtwkr.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static boolean isClient() {
        return FMLCommonHandler.instance().getSide().equals(Side.CLIENT);
    }

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
