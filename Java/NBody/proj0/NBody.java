public class NBody {
    
    /**the method for read txt file for planet's radius */
    public static double readRadius(String file) {
        In in = new In (file);
        int index = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /**the method for read bodies about planet's information */
    public static Body[] readBodies(String file) {
        In in = new In (file);
        int index = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[index];
        
        for (int i = 0; i < index; i++) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xVel = in.readDouble();
            double yVel = in.readDouble();
            double mass = in.readDouble();
            String name = in.readString();

            bodies[i] = new Body(xPos, yPos, xVel, yVel, mass, name);       
        }
        return bodies;

    }

    /**Create main method */
    public static void main(String[] args) {
        
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        

        
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg" );
        StdDraw.show();


        for (Body element : bodies) {
            element.draw();
        }

        StdDraw.enableDoubleBuffering();

        for (double setTime = 0; setTime <= T; setTime += dt) {
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.setScale(-radius, radius);
            StdDraw.picture(0, 0, "images/starfield.jpg" );

            for (Body element : bodies) {
                element.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);

            

        }
        StdOut.printf("%d\n", bodies.length);
            StdOut.printf("%.2e\n", radius);
            for (int i = 0; i < bodies.length; i++) {
                StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
            }
    }
}