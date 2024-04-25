import java.util.Arrays;
import java.util.Random;


class Boid {
    private Random random = new Random();

    final int colorR = random.nextInt(100, 256);
    final int colorG = random.nextInt(100, 256);
    final int colorB = random.nextInt(100, 256);


    private int MAX_VELOCITY = 5;

    private float PERCEPTION_DISTANCE = 50; // radius where boid checks for other boids to align
    private double MAX_FORCE = 0.2; // max amount of steering a boid can do at once

    public Vector2 position = new Vector2(); // vector that is iinitialized to zero
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();
    // acceleration gets reset to 0 every frame and calculated byu the three rules
    // to then add to the velocity

    Boid(int screen_width, int screen_height) {

        this.position.v = new double[]{random.nextInt(screen_width), random.nextInt(screen_height)};
        this.velocity.randomize(-MAX_VELOCITY, MAX_VELOCITY);
        // this.velocity.normalize();

    }

    public void align(Boid[] boids, float multiplier, float perception_distance_multiplier) {
        int total = 0;
        Vector2 avg = new Vector2(); // desired velocity to steer to
        for (int i = 0; i < boids.length; i++) {
            Boid b = boids[i];

            if (b == this) continue; // avoid to count for the same boid

            // check if b is in vicinity
            float d = (float)Math.sqrt( Math.pow(b.position.x() - this.position.x(), 2) + Math.pow(b.position.y() - this.position.y(), 2) );
            if (d > PERCEPTION_DISTANCE * perception_distance_multiplier)
                continue;

            avg.add(b.velocity);
            total++;
        }
        if (total > 0) {
            avg.v[0] /= total;
            avg.v[1] /= total;
            avg.setMag(MAX_VELOCITY); // to not slow down all boids

            // steer towards average
            avg.sub(this.velocity); // remove current velocity
            avg.limit(this.MAX_FORCE); // avoid to steer to it immidiately // NOTE: test multiplying the multiplier with the max force 
        }

        avg.v[0] *= multiplier;
        avg.v[1] *= multiplier;

        this.acceleration.add(avg);
    }

    public void cohesion(Boid[] boids, float multiplier, float perception_distance_multiplier) {
        int total = 0;
        Vector2 avg = new Vector2(); // desired velocity to steer to
        for (int i = 0; i < boids.length; i++) {
            Boid b = boids[i];

            if (b == this) continue; // avoid to count for the same boid

            // check if b is in vicinity
            float d = (float)Math.sqrt( Math.pow(b.position.x() - this.position.x(), 2) + Math.pow(b.position.y() - this.position.y(), 2) );
            if (d > PERCEPTION_DISTANCE * perception_distance_multiplier)
                continue;

            avg.add(b.position);
            total++;
        }
        if (total > 0) {
            avg.v[0] /= total;
            avg.v[1] /= total;
            avg.sub(this.position);
            avg.setMag(MAX_VELOCITY);

            // steer towards average
            avg.sub(this.velocity);
            avg.limit(this.MAX_FORCE);
        }

        avg.v[0] *= multiplier;
        avg.v[1] *= multiplier;

        this.acceleration.add(avg);
    }

    public void separation(Boid[] boids, float multiplier, float perception_distance_multiplier) {
        int total = 0;
        Vector2 avg = new Vector2(); // desired velocity to steer to
        for (int i = 0; i < boids.length; i++) {
            Boid b = boids[i];

            if (b == this) continue; // avoid to count for the same boid

            // check if b is in vicinity
            float d = (float)Math.sqrt( Math.pow(b.position.x() - this.position.x(), 2) + Math.pow(b.position.y() - this.position.y(), 2) );
            if (d > PERCEPTION_DISTANCE * perception_distance_multiplier)
                continue;

            Vector2 diff = new Vector2();
            diff.v = this.position.v.clone();
            diff.sub(b.position); // vector points from b to this
            if (d != 0) {
                diff.v[0] /= d; // make it inversely proportional to the distance
                diff.v[1] /= d;
            }


            avg.add(diff);
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

        avg.v[0] *= multiplier;
        avg.v[1] *= multiplier;

        this.acceleration.add(avg);
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

        // reset acceleration (wil be set by three rules)
        this.acceleration.v = new double[2];

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

