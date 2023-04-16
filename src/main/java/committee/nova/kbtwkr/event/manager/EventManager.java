package committee.nova.kbtwkr.event.manager;

import committee.nova.kbtwkr.event.impl.KeyBindingRegisterEvent;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("kbtwkr.event.EventManager")
public class EventManager {
    private static final EventManager INSTANCE = new EventManager();

    private final EventList<KeyBindingRegisterEvent> listKeyBindingRegister = new EventList<>();

    @ZenMethod
    public static EventManager getInstance() {
        return INSTANCE;
    }

    @ZenMethod
    public void clear() {
        listKeyBindingRegister.clear();
    }

    public boolean hasRegister() {
        return listKeyBindingRegister.hasHandlers();
    }

    public void publishRegister(KeyBindingRegisterEvent event) {
        listKeyBindingRegister.publish(event);
    }

    @ZenMethod
    public IEventHandle onKeyBindingRegister(IEventHandler<KeyBindingRegisterEvent> handler) {
        return listKeyBindingRegister.add(handler);
    }
}
