package committee.nova.kbtwkr.event.impl;

import committee.nova.kbtwkr.keybinding.KeyBinding;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("kbtwkr.event.KeyBindingRegisterEvent")
public class KeyBindingRegisterEvent {
    private final KeyBindingRegisterEventInternal inner;

    public KeyBindingRegisterEvent(KeyBindingRegisterEventInternal inner) {
        this.inner = inner;
    }

    @ZenMethod
    public void addKeyBinding(KeyBinding binding) {
        inner.addKeyBinding(binding);
    }
}
