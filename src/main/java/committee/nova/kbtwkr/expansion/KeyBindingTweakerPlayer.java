package committee.nova.kbtwkr.expansion;

import committee.nova.kbtwkr.KeyBindingTweaker;
import committee.nova.kbtwkr.capabilities.api.IKeyBindState;
import committee.nova.kbtwkr.keybinding.KeyBinding;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@ZenRegister
@ZenExpansion("crafttweaker.player.IPlayer")
public class KeyBindingTweakerPlayer {
    @ZenMethod
    public static int getPressedTimeServer(IPlayer player, KeyBinding key) {
        final EntityPlayer playerInternal = CraftTweakerMC.getPlayer(player);
        if (playerInternal == null) return 0;
        final IKeyBindState state = playerInternal.getCapability(KeyBindingTweaker.kbtwkrCap, null);
        if (state == null) return 0;
        final Map<String, Integer> s = state.getStates();
        final String desc = key.getInner().getDescKey();
        return s.getOrDefault(desc, 0);
    }

    @ZenMethod
    public static boolean isKeyDownServer(IPlayer player, KeyBinding key) {
        return getPressedTimeServer(player, key) > 0;
    }
}
