package committee.nova.kbtwkr.keybinding;

import committee.nova.kbtwkr.KeyBindingTweaker;
import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
@ZenClass("kbtwkr.keybinding.KeyBinding")
public class KeyBinding {
    private final KeyBindingProxy inner;

    private KeyBinding(String descKey, IConflictContext ctx, IModifier mod, int keyCode, String category, boolean shouldSync) {
        this.inner = new KeyBindingProxy(descKey, ctx, mod, keyCode, category, shouldSync);
    }

    @ZenMethod
    public static KeyBinding createNormal(String descKey, IConflictContext ctx, IModifier mod, int keyCode, String category) {
        return new KeyBinding(descKey, ctx, mod, keyCode, category, false);
    }

    @ZenMethod
    public static KeyBinding createSyncable(String descKey, IConflictContext ctx, IModifier mod, int keyCode, String category) {
        return new KeyBinding(descKey, ctx, mod, keyCode, category, true);
    }

    public KeyBindingProxy getInner() {
        return inner;
    }

    @ZenMethod
    public boolean shouldSync() {
        return inner.shouldSync();
    }

    @ZenMethod
    public boolean isKeyDown() {
        return KeyBindingTweaker.isClient() && inner.isKeyDown();
    }

    @ZenMethod
    public boolean isPressed() {
        return KeyBindingTweaker.isClient() && inner.isPressed();
    }

    @ZenRegister
    @ZenClass("kbtwkr.keybinding.IConflictContext")
    public interface IConflictContext {
        IKeyConflictContext getCtx();
    }

    @ZenRegister
    @ZenClass("kbtwkr.keybinding.ConflictContext")
    public static class ConflictContext {
        @ZenProperty
        public static IConflictContext UNIVERSAL = () -> KeyConflictContext.UNIVERSAL;

        @ZenProperty
        public static IConflictContext IN_GAME = () -> KeyConflictContext.IN_GAME;

        @ZenProperty
        public static IConflictContext GUI = () -> KeyConflictContext.GUI;
    }

    @ZenRegister
    @ZenClass("kbtwkr.keybinding.IModifier")
    public interface IModifier {
        KeyModifier getModifier();
    }

    @ZenRegister
    @ZenClass("kbtwkr.keybinding.Modifier")
    public static class Modifier {
        @ZenProperty
        public static IModifier CONTROL = () -> KeyModifier.CONTROL;

        @ZenProperty
        public static IModifier SHIFT = () -> KeyModifier.SHIFT;

        @ZenProperty
        public static IModifier ALT = () -> KeyModifier.ALT;

        @ZenProperty
        public static IModifier NONE = () -> KeyModifier.NONE;
    }
}
