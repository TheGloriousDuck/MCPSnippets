package exampleclient.module;

import net.minecraft.client.Minecraft;

public class Module {
	
	public String name;
	public Minecraft mc = Minecraft.getMinecraft();
	public boolean enabled = true;
	
	public Module(String name) {
		this.name = name;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void toggle() {
		enabled = !enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
