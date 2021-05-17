// Go to net.minecraft.client.gui.inventory.GuiContainer.java

// Go to the bottom of the function drawScreen

public void drawScreen(int mouseX, int mouseY, float partialTicks)
{
    
	// Around here

    if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
    {
        ItemStack itemstack1 = this.theSlot.getStack();
        this.renderToolTip(itemstack1, mouseX, mouseY);
    }

    // Start drawing image

    glDisable(GL_DEPTH_TEST);
	glEnable(GL_BLEND);
	glDepthMask(false);
	OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
	this.mc.getTextureManager().bindTexture(new ResourceLocation("exampleclient/yourimage.png"));
	Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height); // x and y pos of your image, how big you want it
	glDepthMask(true);
	glDisable(GL_BLEND);
	glEnable(GL_DEPTH_TEST);
	glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    
	// Finish drawing image

    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
    RenderHelper.enableStandardItemLighting();
}