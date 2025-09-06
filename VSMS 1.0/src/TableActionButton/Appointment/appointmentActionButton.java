package TableActionButton.Appointment;

import java.awt.Color;
import java.awt.Font;
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
public class appointmentActionButton extends JButton {

    private boolean mousePress;

    public appointmentActionButton() {
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
        int boxWidth = (int) (width * 0.5); // Button Width
        int x = (width - boxWidth) / 2; // Center it

        if (mousePress) {
            g2.setColor(new Color(128, 128, 255));
        } else {
            g2.setColor(new Color(0, 0, 255));
        }

        g2.fillRoundRect(x, 0, boxWidth, height, 15, 15);
        g2.dispose();
        super.paintComponent(g);
    }
}
