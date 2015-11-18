package view.element;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import view.screen.AbstractScreen;
import view.screen.CreditsScreen;
import view.screen.HelpScreen;
import view.screen.SettingsScreen;

public class Buttons extends AbstractElement {

	private AbstractScreen currentScreen;

	public Buttons(GridPane pane, AbstractScreen screen) {
		super(pane);
		findResources();
		this.currentScreen = screen;
		makePane();
	}

	@Override
	protected void makePane() {
		Button helpButton = makeButton(myResources.getString("help"), 
				e -> currentScreen.setNextScreen(new HelpScreen()));
		Button settingsButton = makeButton(myResources.getString("settings"), 
				e -> currentScreen.setNextScreen(new SettingsScreen()));
		Button creditsButton = makeButton(myResources.getString("credits"), 
				e -> currentScreen.setNextScreen(new CreditsScreen()));
		pane.add(helpButton, 0, 0);
		pane.add(settingsButton, 1, 0);
		pane.add(creditsButton, 2, 0);
		pane.setHgap(Integer.parseInt(myResources.getString("hgap")));
	}
	
	protected Button makeButton(String name, EventHandler<MouseEvent> handler) {
		Button button = new Button(name);
		button.setFont(textFont);
		button.setOnMouseClicked(handler);
		button.setFocusTraversable(false);
		return button;
	}
}
