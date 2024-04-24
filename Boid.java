import java.util.Arrays;
import java.util.Random;


class Boid {
    private Random random = new Random();

    private int MAX_VELOCITY = 5;
    private int MIN_VELOCITY = -MAX_VELOCITY;

    private int PERCEPTION_DISTANCE = 50; // radius where boid checks for other boids to align
    private double MAX_FORCE = 0.2; // max amount of steering a boid can do at once

    public Vector2 position = new Vector2(); // vector that is iinitialized to zero
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();

    Boid(int screen_width, int screen_height) {

        this.position.v = new double[]{random.nextInt(screen_width), random.nextInt(screen_height)};
        this.velocity.randomize(MIN_VELOCITY, MAX_VELOCITY);
        //this.velocity.normalize();
        //this.acceleration = new int[]{random.nextInt(MAX_VELOCITY), random.nextInt(MAX_VELOCITY)};

    }

    public void align(Boid[] boids) {
        int total = 0;
        Vector2 avg = new Vector2(); // desired velocity to steer to
        for (int i = 0; i < boids.length; i++) {
            Boid b = boids[i];

            if (b == this) continue; // avoid to count for the same boid

            // check if b is in vicinity
            int d = (int)Math.sqrt( Math.pow(b.position.x() - this.position.x(), 2) + Math.pow(b.position.y() - this.position.y(), 2) );
            if (d > PERCEPTION_DISTANCE)
                continue;

            avg.add(b.velocity);

            total++;
        }
        if (total > 0) {
            avg.v[0] /= total;
            avg.v[1] /= total;
            avg.setMag(MAX_VELOCITY);

            // steer towards average
            avg.sub(this.velocity);
            avg.limit(this.MAX_FORCE);
        }

        this.acceleration.v = avg.v;
    }

    public void update(int screen_width, int screen_height) {
        this.position.add(velocity);
        this.velocity.add(this.acceleration);

        // wrap around the screen
        if (this.position.x() > screen_width) {
            this.position.v[0] = 0;
        }
        else if (this.position.x() < 0) {
            this.position.v[0] = screen_width;
        }
        if (this.position.y() > screen_height) {
            this.position.v[1] = 0;
        }
        else if (this.position.y() < 0) {
            this.position.v[1] = screen_height;
        }

    }

    public static void main(String[] args) {
        Boid b = new Boid(800, 600);

        // print position vector
        System.out.println("before");
        b.position.print();
        b.update(0, 0);
        System.out.println("after");
        b.position.print();
    }

}

