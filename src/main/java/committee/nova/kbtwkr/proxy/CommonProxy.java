package committee.nova.kbtwkr.proxy;

import committee.nova.kbtwkr.capabilities.api.IKeyBindState;
import committee.nova.kbtwkr.capabilities.impl.KeyBindState;
import committee.nova.kbtwkr.common.KeyBindingCommonHandler;
import committee.nova.kbtwkr.event.impl.KeyBindingRegisterEventInternal;
import committee.nova.kbtwkr.keybinding.KeyBinding;
import committee.nova.kbtwkr.network.handler.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Set;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IKeyBindState.class, new KeyBindState.Storage(), KeyBindState.Impl::new);
        NetworkHandler.init();
    }

    public void init(FMLInitializationEvent e) {
        final KeyBindingRegisterEventInternal event = new KeyBindingRegisterEventInternal();
        MinecraftForge.EVENT_BUS.post(event);
        final Set<KeyBinding> keys = event.getKeyBindings();
        for (final KeyBinding k : keys) KeyBindingCommonHandler.tryAddSyncable(k);
    }
}
