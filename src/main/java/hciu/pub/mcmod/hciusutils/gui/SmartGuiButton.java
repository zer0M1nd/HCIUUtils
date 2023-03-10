package hciu.pub.mcmod.hciusutils.gui;

import com.google.common.base.Supplier;

import hciu.pub.mcmod.hciusutils.gui.render.ButtonTextureDrawer;
import net.minecraft.client.Minecraft;

public class SmartGuiButton extends SmartGuiComponentBase {

	private Supplier<String> text = () -> "";
	private boolean enabled = true;

	public SmartGuiButton(ISmartGuiComponent holder) {
		super(holder);
		setTextureDrawer(new ButtonTextureDrawer(this, ButtonTextureDrawer.makeFlexibleSubs(this, 3,
				SmartGuiConstants.VANILLA_TEXTURE_WIDGETS, 0, 46, 200, 20, 0, 20)));
	}

	public SmartGuiButton(ISmartGuiComponent holder, String text) {
		this(holder);
		setText(text);
	}

	public SmartGuiButton(ISmartGuiComponent holder, Supplier<String> text) {
		this(holder);
		setText(text);
	}

	@Override
	public void drawSelf() {
		super.drawSelf();
		int j = 14737632;

		if (!this.enabled) {
			j = 10526880;
		} else if (this.checkMouse()) {
			j = 16777120;
		}
		Minecraft mc = Minecraft.getMinecraft();
		this.drawCenteredString(mc.fontRenderer, getText(), this.actualX + this.sizeX / 2,
				this.actualY + (this.sizeY - 8) / 2, j);

	}

	public boolean isEnabled() {
		return enabled;
	}

	public ISmartGuiComponent setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
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
}
