package hciu.pub.mcmod.hciusutils.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hciu.pub.mcmod.hciusutils.gui.render.AbstractTextureDrawer;
import net.minecraft.client.gui.Gui;

public class SmartGuiComponentBase extends Gui implements ISmartGuiComponent {

	protected int sizeX, sizeY, relativeX, relativeY, actualX, actualY;
	protected boolean visible = true;
	protected ISmartGuiComponent holder;
	protected AbstractTextureDrawer<?> texture;
	private ISmartGuiComponent focus = null;
	private List<ISmartGuiComponent> components;

	private Consumer<? extends SmartGuiComponentBase> resizeAction = null;

	public SmartGuiComponentBase(ISmartGuiComponent holder) {
		this.holder = holder;
		texture = AbstractTextureDrawer.createEmpty(this);
		components = new ArrayList<>();
	}

	@Override
	public ISmartGuiComponent setFocus(ISmartGuiComponent focus) {
		this.focus = focus;
		return this;
	}

	protected void addComponent(ISmartGuiComponent comp) {
		components.add(comp);
	}

	public ISmartGuiComponent setTextureDrawer(AbstractTextureDrawer<?> drawer) {
		texture = drawer;
		return this;
	}

	public ISmartGuiComponent setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getSizeX() {
		return sizeX;
	}

	@Override
	public int getSizeY() {
		return sizeY;
	}

	@Override
	public int getActualX() {
		return actualX;
	}

	@Override
	public int getActualY() {
		return actualY;
	}

	@Override
	public int getRelativeX() {
		return relativeX;
	}

	@Override
	public int getRelativeY() {
		return relativeY;
	}

	@Override
	public void drawSelf() {
		texture.draw();
	}

	@Override
	public void drawAll() {
		drawSelf();
		for (ISmartGuiComponent c : components) {
			if (c.isVisible()) {
				c.drawAll();
			}
		}
	}

	@Override
	public void onKeyPressed(char typedChar, int keyCode) {
		if (focus != null) {
			focus.onKeyPressed(typedChar, keyCode);
		}
	}

	@Override
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		focus = null;
		for (int i = components.size() - 1; i >= 0; i--) {
			ISmartGuiComponent c = components.get(i);
			if (c.checkMouse() && c.isVisible()) {
				focus = c;
				c.onMouseClicked(mouseX - c.getRelativeX(), mouseY - c.getRelativeY(), mouseButton);
				break;
			}
		}
	}

	@Override
	public ISmartGuiComponent getHolder() {
		return holder;
	}

	@Override
	public ISmartGuiComponent getFocus() {
		return focus;
	}

	private void updateActual() {
		if (getHolder() == null) {
			actualX = relativeX;
			actualY = relativeY;
		} else {
			actualX = relativeX + getHolder().getActualX();
			actualY = relativeY + getHolder().getActualY();
		}
	}

	public ISmartGuiComponent setX(int relativeX) {
		this.relativeX = relativeX;
		updateActual();
		return this;
	}

	public ISmartGuiComponent setY(int relativeY) {
		this.relativeY = relativeY;
		updateActual();
		return this;
	}

	public ISmartGuiComponent setPos(int x, int y) {
		relativeX = x;
		relativeY = y;
		updateActual();
		return this;
	}

	public ISmartGuiComponent setSize(int x, int y) {
		this.sizeX = x;
		this.sizeY = y;
		return this;
	}

	public ISmartGuiComponent setBounds(int x, int y, int sx, int sy) {
		setSize(sx, sy);
		setPos(x, y);
		return this;
	}

	public ISmartGuiComponent setCorners(int x, int y, int x1, int y1) {
		setSize(x1 - x, y1 - y);
		setPos(x, y);
		return this;
	}

	public ISmartGuiComponent setCenter(int x, int y) {
		setPos(x - sizeX / 2, y - sizeY / 2);
		return this;
	}

	public ISmartGuiComponent setCenterSize(int x, int y, int sx, int sy) {
		setSize(sx, sy);
		setCenter(x, y);
		return this;
	}

	@Override
	public void onResizeAll() {
		onResizeSelf();
		for (ISmartGuiComponent c : components) {
			c.onResizeAll();
		}
	}

	@Override
	public void onResizeSelf() {
		if (this.resizeAction != null) {
			((Consumer<SmartGuiComponentBase>) this.resizeAction).accept(this);
		}
	}

	public SmartGuiComponentBase setResizeAction(Consumer<? extends SmartGuiComponentBase> action) {
		this.resizeAction = action;
		return this;
	}

	@Override
	public List<String> getTooltipAll() {
		for (ISmartGuiComponent c : components) {
			List<String> t = c.getTooltipAll();
			if (t != null) {
				return t;
			}
		}
		return getTooltipSelf();
	}

	@Override
	public void onKeyReleased(char typedChar, int keyCode) {
		if (focus != null) {
			focus.onKeyReleased(typedChar, keyCode);
		}
	}

	@Override
	public void onGuiClosed() {
		for (ISmartGuiComponent c : components) {
			c.onGuiClosed();
		}
	}

}
