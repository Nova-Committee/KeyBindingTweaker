package committee.nova.kbtwkr.event.handler;

import committee.nova.kbtwkr.KeyBindingTweaker;
import committee.nova.kbtwkr.capabilities.impl.KeyBindState;
import committee.nova.kbtwkr.event.impl.KeyBindingRegisterEvent;
import committee.nova.kbtwkr.event.impl.KeyBindingRegisterEventInternal;
import committee.nova.kbtwkr.event.manager.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ForgeEventHandler {
    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof EntityPlayer)
            e.addCapability(new ResourceLocation(KeyBindingTweaker.MODID, KeyBindingTweaker.MODID), new KeyBindState.Provider());
    }

    @SubscribeEvent
    public static void register(KeyBindingRegisterEventInternal e) {
        if (EventManager.getInstance().hasRegister())
            EventManager.getInstance().publishRegister(new KeyBindingRegisterEvent(e));
    }
}
