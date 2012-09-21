package PG1AStarAlgGUI;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileMouseAdapter extends MouseAdapter {
    private static TileButton mouseCurrentButton;

    @Override
    public void mouseEntered(MouseEvent event) {
        TileButton botton = (TileButton) event.getSource();
        TileMouseAdapter.mouseCurrentButton = botton;

        botton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent event) {
        TileButton botton = (TileButton) event.getSource();
        TileMouseAdapter.mouseCurrentButton = null;
        botton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        TileButton botton = (TileButton) event.getSource();
        String namePreBotton = botton.getName();
        if (TileMouseAdapter.mouseCurrentButton != null) {
            String nameCurrentBotton = TileMouseAdapter.mouseCurrentButton.getName();

            botton.setTileButtonIcon(nameCurrentBotton);
            botton.setName(nameCurrentBotton);

            TileMouseAdapter.mouseCurrentButton.setTileButtonIcon(namePreBotton);
            TileMouseAdapter.mouseCurrentButton.setName(namePreBotton);
        }
    }
}
