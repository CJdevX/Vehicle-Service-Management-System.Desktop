package TableActionButton.Waiting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author chala
 */
public class waitingActionButton extends JButton {

    private boolean mousePress;

    public waitingActionButton() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePress = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePress = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (mousePress) {
            g2.setColor(new Color(128, 128, 255));
        } else {
            g2.setColor(new Color(0, 0, 255));
        }
        g2.fillRoundRect(0, 0, width, height, 20, 20);
        g2.dispose();
        super.paintComponent(g); // Paint normal background first
    }
}
