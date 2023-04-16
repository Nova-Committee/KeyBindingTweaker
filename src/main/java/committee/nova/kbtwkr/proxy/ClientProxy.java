package committee.nova.kbtwkr.proxy;

import committee.nova.kbtwkr.KeyBindingTweaker;
import committee.nova.kbtwkr.common.KeyBindingCommonHandler;
import committee.nova.kbtwkr.event.impl.KeyBindingRegisterEventInternal;
import committee.nova.kbtwkr.keybinding.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void init(FMLInitializationEvent e) {
        final KeyBindingRegisterEventInternal event = new KeyBindingRegisterEventInternal();
        MinecraftForge.EVENT_BUS.post(event);
        final Set<KeyBinding> keys = event.getKeyBindings();
        for (final KeyBinding k : keys) {
            KeyBindingCommonHandler.tryAddSyncable(k);
            try {
                ClientRegistry.registerKeyBinding(k.getInner().getKeyBinding());
            } catch (Exception ex) {
                KeyBindingTweaker.getLogger().error("Error occurred in client keybinding registration. Please check your scripts.", ex);
            }
        }
    }
}
