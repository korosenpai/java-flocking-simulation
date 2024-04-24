import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class Vector2 {
    public double[] v = new double[2];

    public double x() {
        return v[0];
    }

    public double y() {
        return v[1];
    }

    public void randomize(double min, double max) {
        this.v[0] = ThreadLocalRandom.current().nextDouble(min, max);
        this.v[1] = ThreadLocalRandom.current().nextDouble(min, max);
    }

    public void add(Vector2 vec) {
        this.v[0] += vec.v[0];
        this.v[1] += vec.v[1];
    }

    public void sub(Vector2 vec) {
        this.v[0] -= vec.v[0];
        this.v[1] -= vec.v[1];
    }

    public void multiply(Vector2 vec) {
        this.v[0] *= vec.v[0];
        this.v[1] *= vec.v[1];

    }

    public void divide(Vector2 vec) {
        this.v[0] /= vec.v[0];
        this.v[1] /= vec.v[1];

    }

    public double magnitude() {
        return (double)Math.sqrt(Math.pow(this.x(), 2) + Math.pow(this.y(), 2));
    }

    public void normalize() {
        double mag = magnitude();
        if (mag == 0) return;

        this.v[0] /= mag;
        this.v[1] /= mag;
    }

    public void setMag(double magnitude) {
        this.normalize();
        this.v[0] *= magnitude;
        this.v[1] *= magnitude;
    }

    public void limit(double max) {
        // https://p5js.org/reference/#/p5.Vector/limit
        // something like this?

        double mag = magnitude();

        if (mag > max) {
            double scale = max / mag;
            this.v[0] *= scale;
            this.v[1] *= scale;
        }

    }

    public void print() {
        System.out.println(Arrays.toString(this.v));
    }

}
