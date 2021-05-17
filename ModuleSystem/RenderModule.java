package exampleclient.module;

import org.lwjgl.input.Mouse;

import exampleclient.ExampleClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class RenderModule extends Module {
	
	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer font;
	public int xPos = 10;
	public int yPos = 10;

	public RenderModule(String name) {
		super(name);
		font = mc.fontRendererObj;
		ExampleClient.INSTANCE.eventManager.register(this);
		this.enabled = true;
	}
	
	public RenderModule(String name, int defaultX, int defaultY) {
		super(name);
		font = mc.fontRendererObj;
		this.xPos = defaultX;
		this.yPos = defaultY;
		ExampleClient.INSTANCE.eventManager.register(this);
		this.enabled = true;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public void setY(int y) {
		this.yPos = y;
	}
	
	public int getWidth() {
		return 0;
	}
	
	public int getHeight() {
		return 0;
	}
	
	public void render() {
		
	}
	
	public void renderDummy() {
		
	}
}
