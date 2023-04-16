package committee.nova.kbtwkr.event.impl;

import committee.nova.kbtwkr.keybinding.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.HashSet;
import java.util.Set;

public class KeyBindingRegisterEventInternal extends Event {
    private final Set<KeyBinding> keyBindings = new HashSet<>();

    public void addKeyBinding(KeyBinding binding) {
        keyBindings.add(binding);
    }

    public Set<KeyBinding> getKeyBindings() {
        return keyBindings;
    }
}
