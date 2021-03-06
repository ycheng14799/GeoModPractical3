import java.awt.*;
import java.lang.*;
import java.util.*;
import java.awt.geom.Line2D;

public class MyPolygon extends Polygon {

    public static final long serialVersionUID = 24362462L;
    public Polygon Elevated;
    public boolean elevated = false;
    private double tempX[] = new double[4];
    private double tempY[] = new double[4];
	public int numElevations = 0; 

    public MyPolygon() {
    }

    public MyPolygon(int x[],int y[],int n) {
    }

    /////////////////////////////////////////////////////////////

    //This is a convenience method for ensuring that tempX and tempY
    //grow in an extensible manner.  If newSize is greater than the 
    //current capacity then tempX and tempY are reallocated to double the 
    //until they reach the required capacity.  This reallocation includes 
    //copying all data to the new location.
    private void resizeTemp(int newSize)
    {
        assert(tempX.length == tempY.length);
        while (newSize > tempX.length)
        {
            //Double the capacity of the array
            //Make more space
            double [] copy_x=new double[tempX.length*2];
            double [] copy_y=new double[tempX.length*2];
            //Copy data across
            System.arraycopy(tempX, 0, copy_x, 0, tempX.length);
            System.arraycopy(tempY, 0, copy_y, 0, tempX.length);
            //Swing the references back to the original object
            tempX=copy_x;
            tempY=copy_y;
           
            //System.out.println("Capacity increased to "+tempX.length);
        }
        assert(tempX.length == tempY.length);
    }

    public void elevateOnce() {
      //Input comes in the form of a Java Polygon (points are stored as integers)
      //Output is also in the form of a Java Polygon called Elevated
      //See http://download.oracle.com/javase/1.5.0/docs/api/java/awt/Polygon.html for Polygon details
      //As degree elevation progresses, the working data is kept in
      //double-precision floating point

      int np=npoints;
		if(!elevated) {
			//Begin new elevated polygon
			Elevated = new Polygon();
			//Make vector(s) of points which are of size npoints+1 (to allow room for elevation).
			resizeTemp(np+1);
			for (int i=0; i<np; i++) {
			  tempX[i] =  xpoints[i];
			  tempY[i] =  ypoints[i];
			}
			numElevations = 1; 
			elevated = true; 
		} else {
			//Make vector(s) of points which are of size npoints+1 (to allow room for elevation).
			resizeTemp(Elevated.npoints+1);
			np=Elevated.npoints; 
			for (int i=0; i<np; i++) {
			  tempX[i] =  Elevated.xpoints[i];
			  tempY[i] =  Elevated.ypoints[i];
			}
			numElevations++; 
		}
        

      /* YOUR CODE GOES HERE */
      System.out.println("Degree elevation: " + numElevations);

	  // Point at index 0 should be unchanged 
	  // Start at index 1 
	  double oldPiX = tempX[0], oldPiY = tempY[0]; 
	  double newPiX, newPiY;
	  for(int i=1; i<np+1; i++) {
		newPiX = (((double)i)/((double)np))*oldPiX + (((double)np-i)/((double)np))*tempX[i];
		newPiY = (((double)i)/((double)np))*oldPiY + (((double)np-i)/((double)np))*tempY[i];
		oldPiX = tempX[i];
		oldPiY = tempY[i];
		tempX[i] = newPiX;
		tempY[i] = newPiY;
	  }

      //Copy all npoints+1 points back into the Elevated polygon
      Elevated.reset();
      for(int i=0;i<=np; i++) {
        Elevated.addPoint((int)(tempX[i]+0.5),
                          (int)(tempY[i]+0.5));
      }
    }
}
