import java.awt.*;
import javax.swing.*;

public class H3D extends JPanel {
    private static final int PREF_W = 400;
    private static final int PREF_H = 400;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        int size = Math.min(w, h) / 2;
        int x = w / 2 - size / 2;
        int y = h / 2 - size / 2;

        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, size, size / 10);
        g2.drawRect(x, y + size / 2, size, size / 10);
        g2.drawRect(x + size / 2, y, size / 10, size);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("H3D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new H3D());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}
