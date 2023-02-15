package gui;

import javax.swing.*;
import java.awt.*;

public class ChooseLevelImage extends JPanel {

    private final Image icon; // image icon
    private String levelName; // level name as string

    public ChooseLevelImage(String imagePath){
        this.levelName = levelName;

        icon = new ImageIcon(imagePath).getImage();
        // sets the dimension of image icon
        this.setPreferredSize(new Dimension(100,100));

    }
    @Override
    protected void paintComponent(Graphics g) {
        // draws the image icon
        super.paintComponent(g);
        g.drawImage(icon,0,0,null);
    }
}
