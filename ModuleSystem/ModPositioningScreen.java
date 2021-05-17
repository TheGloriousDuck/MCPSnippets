package exampleclient.gui;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import exampleclient.ExampleClient;
import exampleclient.module.RenderModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ModPositioningScreen extends GuiScreen {

	private int smX, smY;
	private boolean dragged = false;
	protected boolean hovered;
	
	private Optional<RenderModule> selectedModule = Optional.empty();

    private int prevX, prevY;
    
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
        this.zLevel = 200;
		
		for (RenderModule m : Starex.INSTANCE.moduleManager.getRenderModuleList()) {
			if (m.isEnabled()) {
				m.renderDummy();
				
				this.drawHorizontalLine(m.getX() - 2, m.getX() + m.getWidth(), m.getY() - 2, -1);
				this.drawHorizontalLine(m.getX() - 2, m.getX() + m.getWidth(), m.getY() + m.getHeight(), -1);
				
				this.drawVerticalLine(m.getX() - 2, m.getY() - 2, m.getY() + m.getHeight(), -1);
				this.drawVerticalLine(m.getX() + m.getWidth(), m.getY() - 2, m.getY() + m.getHeight(), -1);
				
				// START OF SMOOTH DRAGGING
	
				// Thanks ESS_Si1kn#0481 for pointing out that I forgot to add these back.
				int absoluteX = m.getX();
				int absoluteY = m.getY();
	
				this.hovered = mouseX >= absoluteX && mouseX <= absoluteX + m.getWidth() && mouseY >= absoluteY && mouseY <= absoluteY + m.getHeight();
	
				if (this.hovered) {
					if (dragged) {
						m.setX(m.getX() + mouseX - this.prevX);
						m.setY(m.getY() + mouseY - this.prevY);
	
						adjustBounds(m);
	
						this.prevX = mouseX;
						this.prevY = mouseY;
					}
				}
	
				// END OF SMOOTH DRAGGING
			}
		}
		
		this.smX = mouseX;
        this.smY = mouseY;

        this.zLevel = zBackup;
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        if (selectedModule.isPresent()) {
            moveSelectedRenderBy(x - prevX, y - prevY);
        }

        this.prevX = x;
        this.prevY = y;
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        RenderModule m = selectedModule.get();

        m.setX(m.getX() + offsetX);
        m.setY(m.getY() + offsetY);

        if (m.getX() == 0 << 1) {
            m.setX(1);
            m.setY(m.getY());
        }

        if (m.getY() == 0 << 1) {
        	m.setX(m.getX());
        	m.setY(1);
        }

        adjustBounds(m);
    }
	
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
	private void adjustBounds(RenderModule m) {

        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        int absoluteX = Math.max(0, Math.min(m.getX(), Math.max(screenWidth - m.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(m.getY(), Math.max(screenHeight - m.getHeight(), 0)));

        m.setX(absoluteX);
        m.setY(absoluteY);
    }
	
	@Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        this.prevX = x;
        this.prevY = y;

        // NEEDED FOR SMOOTH DRAGGING
        dragged = true;

        loadMouseOver(x, y);
        super.mouseClicked(x, y, button);
    }
	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {

        // NEEDED FOR SMOOTH DRAGGING
        dragged = false;

        super.mouseReleased(mouseX, mouseY, state);
    }
	
	private void loadMouseOver(int x, int y) {
        this.selectedModule = ExampleClient.INSTANCE.moduleManager.getRenderModuleList().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<RenderModule> {

        private int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        @Override
        public boolean test(RenderModule m) {
            int absoluteX = m.getX();
            int absoluteY = m.getY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + m.getWidth()) {

                if (mouseY >= absoluteY && mouseY <= absoluteY + m.getHeight()) {

                    return true;

                }

            }

            return false;
        }

    }
	
}
