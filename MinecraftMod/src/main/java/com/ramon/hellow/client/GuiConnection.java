package com.ramon.hellow.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiConnection extends GuiScreen {
	private final GuiScreen back;
	protected String name = "Connection";
	
	public GuiConnection(GuiScreen back) {
		this.back = back;
	}
	
	@Override
	public void initGui() {
		this.name = I18n.format("gui.connection.title", new Object[0]);
		this.buttonList.add(new GuiButton(1, this.width / 2 - 150 , this.height / 6 + 12 , 100 , 20 , I18n.format("gui.connection.connect", new Object[0])));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 50 , this.height / 6 + 12 , 100 , 20 , I18n.format("gui.connection.test", new Object[0])));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 100 , this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id==3) {
			this.mc.displayGuiScreen(this.back);
		}
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.name, this.width / 2, 15, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}