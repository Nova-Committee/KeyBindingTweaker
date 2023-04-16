package committee.nova.kbtwkr.network.message;

import committee.nova.kbtwkr.KeyBindingTweaker;
import committee.nova.kbtwkr.capabilities.api.IKeyBindState;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KeyBindingStateSyncMessage implements IMessage {
    private NBTTagCompound tag = new NBTTagCompound();

    public NBTTagCompound getTag() {
        return tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<KeyBindingStateSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(KeyBindingStateSyncMessage message, MessageContext ctx) {
            if (ctx.side != Side.SERVER) return null;
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final NBTTagCompound tag = message.getTag();
            UUID u = new UUID(0L, 0L);
            try {
                u = UUID.fromString(tag.getString("uuid"));
            } catch (Exception ignored) {
            }
            if (!u.equals(player.getUniqueID())) return null;
            final MinecraftServer server = player.getServer();
            if (server == null) return null;
            server.addScheduledTask(() -> {
                final NBTTagList statesList = tag.getTagList("states", 8);
                final List<String> pressed = new ArrayList<>();
                for (final NBTBase p : statesList)
                    if (p instanceof NBTTagString) pressed.add(((NBTTagString) p).getString());
                final IKeyBindState s = player.getCapability(KeyBindingTweaker.kbtwkrCap, null);
                if (s == null) return;
                final Map<String, Integer> states = s.getStates();
                states.keySet().forEach(k -> {
                    if (!pressed.contains(k)) states.remove(k);
                });
                pressed.forEach(p -> states.put(p, states.getOrDefault(p, 0) + 1));
            });
            return null;
        }
    }
}
