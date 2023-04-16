package committee.nova.kbtwkr.common;

import com.google.common.collect.ImmutableList;
import committee.nova.kbtwkr.keybinding.KeyBinding;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeyBindingCommonHandler {
    private static final Set<KeyBinding> syncable = new HashSet<>();

    public static void tryAddSyncable(KeyBinding binding) {
        if (binding.shouldSync()) syncable.add(binding);
    }

    public static List<KeyBinding> getSyncable() {
        return ImmutableList.copyOf(syncable);
    }
}
