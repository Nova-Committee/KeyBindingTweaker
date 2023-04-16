package committee.nova.kbtwkr.capabilities.impl;

import committee.nova.kbtwkr.capabilities.api.IKeyBindState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static committee.nova.kbtwkr.KeyBindingTweaker.kbtwkrCap;

public class KeyBindState {
    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        private final Impl instance = new Impl();
        private final Storage storage = new Storage();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == kbtwkrCap;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == kbtwkrCap ? kbtwkrCap.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            final NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("kbtwkr", storage.writeNBT(kbtwkrCap, instance, null));
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            storage.readNBT(kbtwkrCap, instance, null, nbt.getTagList("kbtwkr", 10));
        }
    }

    public static class Storage implements Capability.IStorage<IKeyBindState> {
        @Override
        @Nonnull
        public NBTBase writeNBT(Capability<IKeyBindState> capability, IKeyBindState instance, EnumFacing side) {
            final NBTTagList list = new NBTTagList();
            final Set<Map.Entry<String, Integer>> states = instance.getStates().entrySet();
            for (final Map.Entry<String, Integer> e : states) {
                final NBTTagCompound tag = new NBTTagCompound();
                tag.setString("key", e.getKey());
                tag.setInteger("pressed", e.getValue());
                list.appendTag(tag);
            }
            return list;
        }

        @Override
        public void readNBT(Capability<IKeyBindState> capability, IKeyBindState instance, EnumFacing side, NBTBase nbt) {
            if (!(nbt instanceof NBTTagList)) return;
            final Map<String, Integer> states = instance.getStates();
            states.clear();
            final NBTTagList list = (NBTTagList) nbt;
            for (final NBTBase b : list) {
                if (!(b instanceof NBTTagCompound)) continue;
                final NBTTagCompound tag = (NBTTagCompound) b;
                final String key = tag.getString("key");
                if (key.isEmpty()) continue;
                states.put(key, tag.getInteger("pressed"));
            }
        }
    }

    public static class Impl implements IKeyBindState {
        private final Map<String, Integer> states = new HashMap<>();

        @Override
        public Map<String, Integer> getStates() {
            return states;
        }
    }
}
