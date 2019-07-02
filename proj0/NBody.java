public class NBody{
	private static String starfield = "./images/starfield.jpg";
	public static double readRadius(String f){
		In in = new In(f);
		int number = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	public static Body[] readBodies(String f){
		In in = new In(f);		
		int i = 0;		
		int number = in.readInt();
		Body[] bs = new Body[number];
		double radius = in.readDouble();
		while (!in.isEmpty()){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String name = in.readString();
			bs[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, name);
			i++;
		}
		return bs;
	}
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		double radius = readRadius(filename);
		Body[] bs = readBodies(filename);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		
		int i = 0;
		double timer = 0;
		while(timer<T){
			double[] xForces = new double[bs.length];
			double[] yForces = new double[bs.length];

			for(i=0; i<bs.length; i++){
				xForces[i] = bs[i].calcNetForceExertedByX(bs);
				yForces[i] = bs[i].calcNetForceExertedByY(bs);
				bs[i].update(dt, xForces[i], yForces[i]);
			}
			for(i=0; i<bs.length; i++){
				bs[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, starfield);
			for(i=0; i<bs.length; i++){
				bs[i].draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
			StdDraw.clear();
			timer= timer+dt;
		}
		/*StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
   			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}*/
	} 
}	