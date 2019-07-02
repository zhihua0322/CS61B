public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public Body(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Body(Body b){
		this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
	}
	public double calcDistance(Body b){
		double dx = this.xxPos - b.xxPos;
		double dy = this.yyPos - b.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}
	public double calcForceExertedBy(Body b){
		double g = 6.67 * Math.pow(10,-11);
		double f = g * this.mass * b.mass / Math.pow(this.calcDistance(b),2);
		return f;
	}
	public double calcForceExertedByX(Body b){
		double f = calcForceExertedBy(b);
		double dx = 0;
		if(this.xxPos > b.xxPos){
			dx = this.xxPos - b.xxPos;
		}
		else dx = b.xxPos - this.xxPos;
		double  fx = f * dx / calcDistance(b);
		return fx;
	}
	public double calcForceExertedByY(Body b){
		double f = calcForceExertedBy(b);
		double dy = 0;
		if(this.yyPos > b.yyPos){
			dy = this.yyPos - b.yyPos;
		}
		else dy = b.yyPos - this.yyPos;
		double  fy = f * dy / calcDistance(b);
		return fy;
	}
	public double calcNetForceExertedByX(Body[] b){
		double fnetx = 0;
		for(int i = 0; i< b.length; i++){
			if(this.equals(b[i]) == false){
				if(this.xxPos > b[i].xxPos)
					fnetx = fnetx + calcForceExertedByX(b[i]);
				else
					fnetx = fnetx - calcForceExertedByX(b[i]);
			}

		}
		return Math.abs(fnetx);
	}
	public double calcNetForceExertedByY(Body[] b){
		double fnety = 0;
		for(int i = 0; i< b.length; i++){
			if(this.equals(b[i]) == false){			
				if(this.yyPos > b[i].yyPos)
					fnety = fnety + calcForceExertedByY(b[i]);
				else
					fnety = fnety - calcForceExertedByY(b[i]);
			}
		}
		return Math.abs(fnety);
	}
	public void update(double dt,double fX,double fY){
		double aX = fX / this.mass;
		double aY = fY / this.mass;
		this.xxVel += aX * dt;
		this.yyVel += aY * dt;
		this.xxPos += this.xxVel * dt;
		this.yyPos += this.yyVel * dt;
	}
	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}
}