package gough.andrew.phasor;

public class Vector {
	private double xVector;
	private double yVector;
	
	public Vector(){
	xVector = 0;
	yVector = 0;
	}
	
	public Vector(double xVector,double yVector){
		this.xVector = xVector;
		this.yVector = yVector;
	}
	
	public double getXVector(){
		return xVector;
	}
	
	public double getYVector(){
		return yVector;
	}
	
	public boolean setXVector(double xVector){
		this.xVector = xVector;
		return true;
	}
	
	public boolean setYVector(double yVector){
		this.yVector = yVector;
		return true;
	}
}
