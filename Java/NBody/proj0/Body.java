public class Body {
    /**Basic variables*/
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double constantForce = 6.67e-11;
    
    /**first constructor */
    public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
                  xxPos = xP;
                  yyPos = yP;
                  xxVel = xV;
                  yyVel = yV;
                  mass = m;
                  imgFileName = img;
              }
    /**Second constructor */
    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    
    /**calculation method for distance non-static ver */
    public double calcDistance(Body b2) {
        double dx = b2.xxPos - this.xxPos;
        double dy = b2.yyPos - this.yyPos;
        double rSquare = Math.pow(dx, 2) + Math.pow(dy, 2);
        double result = Math.sqrt(rSquare);
        return result;    
    }

    /**calculation method for Force non-static ver */
    public double calcForceExertedBy(Body b2) {
        if (this.equals(b2)){
            return 0;
        }
        return constantForce*this.mass*b2.mass / Math.pow((this.calcDistance(b2)), 2);
    }

    /**calculation method for x-direction Force non-static ver */
    public double calcForceExertedByX(Body b2) {
        if (this.equals(b2)) {
            return 0;
        }
        double dx = b2.xxPos - this.xxPos;
        double result = this.calcForceExertedBy(b2) * dx /(this.calcDistance(b2));
        return result; 
    }

    /**calculation method for y-direction Force non-static ver */
    public double calcForceExertedByY(Body b2) {
        if (this.equals(b2)) {
            return 0;
        }
        double dy = b2.yyPos - this.yyPos;
        double result = this.calcForceExertedBy(b2) * dy /(this.calcDistance(b2));
        return result; 
    }

    /**calculation method for x-direction Net Force non-static ver */
    public double calcNetForceExertedByX(Body[] bodys) {
        double netForce = 0;
        for (Body element : bodys) {
            netForce += calcForceExertedByX(element);
        }
        return netForce;
    }

    /**calculation method for y-direction Net Force non-static ver */
    public double calcNetForceExertedByY(Body[] bodys) {
        double netForce = 0;
        for (Body element : bodys) {
            netForce += calcForceExertedByY(element);
        }
        return netForce;
    }

    /** Update for the new information of planets */
    public void update(double  dt, double fx, double fy) {
        double xxAcer = fx / this.mass;
        double yyAcer = fy / this.mass;
        
        this.xxVel = xxVel + dt * xxAcer;
        this.yyVel = yyVel + dt * yyAcer;

        this.xxPos = xxPos + dt * this.xxVel;
        this.yyPos = yyPos + dt * this.yyVel;
    }
    
    /** Drawing planets on the starfield */
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}