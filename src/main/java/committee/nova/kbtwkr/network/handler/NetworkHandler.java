package committee.nova.kbtwkr.network.handler;

import committee.nova.kbtwkr.KeyBindingTweaker;
import committee.nova.kbtwkr.network.message.KeyBindingStateSyncMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(KeyBindingTweaker.MODID);

    private static int nextId = 0;

    public static void init() {
        registerMessage(KeyBindingStateSyncMessage.Handler.class, KeyBindingStateSyncMessage.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        instance.registerMessage(messageHandler, requestMessageType, nextId++, side);
    }
}
