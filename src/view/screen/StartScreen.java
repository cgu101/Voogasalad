package view.screen;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import view.element.Buttons;
import view.element.Icons;
import view.element.Title;
/**
 * Screen for main menu
 * @author David
 *
 */
public class StartScreen extends AbstractScreen {

	private Title t;
	private Icons i;
	private Buttons b;

	public StartScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		this.title = myResources.getString("title");
		makeScene();
		scene = new Scene(root, WIDTH, HEIGHT);
	}

	@Override
	public void run() {
	}

	@Override
	protected void makeScene() {
		GridPane r = new GridPane();
		r.setVgap(Integer.parseInt(myResources.getString("vgap")));
		super.makePanes(3);
		t = new Title(myPanes.get(0), title);
		i = new Icons(myPanes.get(1), this);
		b = new Buttons(myPanes.get(2), this);
		stackVertical(r);
		root = r;
	}

}
