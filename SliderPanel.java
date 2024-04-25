import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import java.awt.Dimension;
import java.awt.Color;

class SliderPanel extends JPanel{
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;

    private Slider alignmentMultiplier;
    private Slider cohesionMultiplier;
    private Slider separationMultiplier;

    SliderPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // set window size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // all drawing from this component will be done in an offscreen painting buffer -> improves performance

        this.requestFocusInWindow();
        // this.setFocusable(true); // to use keyAdapter
        // this.addKeyListener(new MyKeyAdapter());



        this.alignmentMultiplier = new Slider(JSlider.HORIZONTAL, 0, 500, 100, "alignment");
        this.add(this.alignmentMultiplier);

        this.cohesionMultiplier = new Slider(JSlider.HORIZONTAL, 0, 500, 100, "cohesion");
        this.add(this.cohesionMultiplier);

        this.separationMultiplier = new Slider(JSlider.HORIZONTAL, 0, 500, 100, "separation");
        this.add(this.separationMultiplier);
    }

    public void reset() {
        // set all slider at starting val
        alignmentMultiplier.setValue(100);
        cohesionMultiplier.setValue(100);
        separationMultiplier.setValue(100);
    }

    public float getAlignmentMult() {
        return (float)alignmentMultiplier.getValue() / 100; // in percent
    }

    public float getCohesionMult() {
        return (float)cohesionMultiplier.getValue() / 100;
    }

    public float getSeparationMult() {
        return (float)separationMultiplier.getValue() / 100;
    }
}

class Slider extends JSlider {
    Slider(int orientation, int min, int max, int startVal, String tooltip) {
        super(orientation, min, max, startVal);
        setToolTipText(tooltip);
        setMajorTickSpacing(100);
        setMinorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);
    }

}
