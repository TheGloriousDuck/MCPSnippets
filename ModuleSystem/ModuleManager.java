package exampleclient.module;

import java.util.LinkedHashSet;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

import exampleclient.event.EventManager;
import exampleclient.event.EventTarget;
import exampleclient.event.impl.ClientTickEvent;
import exampleclient.event.impl.RenderEvent;
import exampleclient.gui.ModPositioningScreen;
import exampleclient.gui.clickgui.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.inventory.GuiContainer;

public class ModuleManager
{
    public LinkedHashSet<Module> moduleList;
    public LinkedHashSet<RenderModule> renderModuleList;
    public Minecraft mc = Minecraft.getMinecraft();
    
    public ModuleManager() {
        this.moduleList = Sets.newLinkedHashSet();
        this.renderModuleList = Sets.newLinkedHashSet();
        EventManager.register(this);
    }
    
    public void init() {
        final Set<Class<? extends Module>> moduleClazz = new Reflections("exampleclient.module.impl").getSubTypesOf(Module.class);
        moduleClazz.forEach(module -> {
            try {
            	moduleList.add(module.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        });
        
        final Set<Class<? extends RenderModule>> renderClazz = new Reflections("exampleclient.module.impl").getSubTypesOf(RenderModule.class);
        renderClazz.forEach(module -> {
            try {
            	renderModuleList.add(module.newInstance());
            }
            catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return;
        });
    }
    
    public LinkedHashSet<Module> getModuleList() {
		return moduleList;
	}
    
    public LinkedHashSet<RenderModule> getRenderModuleList() {
		return renderModuleList;
	}
    
    @EventTarget
    public void renderHook(RenderEvent e) {
    	for(RenderModule m : renderModuleList) {
    		if (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) {
    			if (m.isEnabled()) {
	    			if (mc.gameSettings.showDebugInfo) return;
	    			m.render();
	    		}
    		}
    	}
    }
    
    @EventTarget
    public void openScreenHook(ClientTickEvent e) {
    	if(Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POSITIONING.isPressed()) {
    		Minecraft.getMinecraft().displayGuiScreen(new ModPositioningScreen());
    	}
    }
}