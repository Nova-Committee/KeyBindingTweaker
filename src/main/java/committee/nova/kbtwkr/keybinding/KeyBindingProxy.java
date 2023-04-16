package committee.nova.kbtwkr.keybinding;

import committee.nova.kbtwkr.keybinding.KeyBinding.IConflictContext;
import committee.nova.kbtwkr.keybinding.KeyBinding.IModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyBindingProxy {
    private final String descKey;
    private final IConflictContext ctx;
    private final IModifier mod;
    private final int keyCode;
    private final String categoryKey;
    private final boolean shouldSync;
    @SideOnly(Side.CLIENT)
    private net.minecraft.client.settings.KeyBinding binding;

    public KeyBindingProxy(String descKey, IConflictContext ctx, IModifier mod, int keyCode, String categoryKey, boolean shouldSync) {
        this.descKey = descKey;
        this.ctx = ctx;
        this.mod = mod;
        this.keyCode = keyCode;
        this.categoryKey = categoryKey;
        this.shouldSync = shouldSync;
    }

    public boolean shouldSync() {
        return shouldSync;
    }

    @SideOnly(Side.CLIENT)
    public net.minecraft.client.settings.KeyBinding getKeyBinding() {
        if (binding == null)
            binding = new net.minecraft.client.settings.KeyBinding(descKey, ctx.getCtx(), mod.getModifier(), keyCode, categoryKey);
        return binding;
    }

    @SideOnly(Side.CLIENT)
    public boolean isKeyDown() {
        return getKeyBinding().isKeyDown();
    }

    @SideOnly(Side.CLIENT)
    public boolean isPressed() {
        return getKeyBinding().isPressed();
    }

    public String getDescKey() {
        return descKey;
    }
}
