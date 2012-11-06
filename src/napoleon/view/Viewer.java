package napoleon.view;

import napoleon.model.player.Player;

public interface Viewer {
	void show(Player[] players);

	void showMessage(String message);
}
