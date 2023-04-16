package committee.nova.kbtwkr.event.handler;

import committee.nova.kbtwkr.common.KeyBindingCommonHandler;
import committee.nova.kbtwkr.keybinding.KeyBinding;
import committee.nova.kbtwkr.network.handler.NetworkHandler;
import committee.nova.kbtwkr.network.message.KeyBindingStateSyncMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class FMLClientEventHandler {
    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.side != Side.CLIENT) return;
        final List<KeyBinding> syncable = KeyBindingCommonHandler.getSyncable();
        final NBTTagList list = new NBTTagList();
        for (final KeyBinding k : syncable)
            if (k.isKeyDown()) list.appendTag(new NBTTagString(k.getInner().getDescKey()));
        final KeyBindingStateSyncMessage msg = new KeyBindingStateSyncMessage();
        final NBTTagCompound tag = msg.getTag();
        tag.setTag("states", list);
        tag.setString("uuid", event.player.getUniqueID().toString());
        NetworkHandler.instance.sendToServer(msg);
    }
}
