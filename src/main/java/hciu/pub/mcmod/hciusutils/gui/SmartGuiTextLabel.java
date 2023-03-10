package hciu.pub.mcmod.hciusutils.gui;

import com.google.common.base.Supplier;

import net.minecraft.client.Minecraft;

public class SmartGuiTextLabel extends SmartGuiComponentBase {

	private Supplier<String> text = () -> "";
	private boolean centered = false;

	public SmartGuiTextLabel(ISmartGuiComponent holder) {
		super(holder);
	}

	public SmartGuiTextLabel(ISmartGuiComponent holder, String text) {
		super(holder);
		setText(text);
	}

	public SmartGuiTextLabel(ISmartGuiComponent holder, Supplier<String> text) {
		super(holder);
		setText(text);
	}

	public String getText() {
		return text.get();
	}

	public ISmartGuiComponent setText(String text) {
		this.text = () -> text;
		return this;
	}

	public ISmartGuiComponent setText(Supplier<String> text) {
		this.text = text;
		return this;
	}

	public boolean isCentered() {
		return centered;
	}

	public SmartGuiTextLabel setCentered(boolean centered) {
		this.centered = centered;
		return this;
	}

	@Override
	public void drawSelf() {
		super.drawSelf();
		Minecraft mc = Minecraft.getMinecraft();
		if (centered) {
			this.drawCenteredString(mc.fontRenderer, getText(), this.actualX + this.sizeX / 2,
					this.actualY + (this.sizeY - 8) / 2, SmartGuiConstants.VANILLA_TEXT_COLOR_ENABLED);
		} else {
			mc.fontRenderer.drawSplitString(getText(), actualX, actualY, sizeX,
					SmartGuiConstants.VANILLA_TEXT_COLOR_ENABLED);
		}
	}

}
