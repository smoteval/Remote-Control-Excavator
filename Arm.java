
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
//import java.lang.Math.abs;



/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry
 */
public class Arm extends Sprite {

    private Rectangle2D rect = null;
    private int numberofscales = 0;

    /// Constructor
    public Arm() {
        super();
        this.rotatable = true;
    }

    
    /**
     * Test if our rectangle contains the point specified.
     */
    public boolean pointInside(Point2D p) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);
        return rect.contains(newPoint) ; 
    }

    protected void drawSprite(Graphics2D g) {
        
        Graphics2D g2 = (Graphics2D)g.create();

        g2.setColor(Color.orange);
        g2.fillRect(-14, -40, 28, 310);
        g2.setColor(Color.black);
        g2.drawRect(-14, -40, 28, 310);
        g2.setColor(Color.darkGray);
        g2.fillOval(-6, -6, 12, 12);
        rect = new Rectangle2D.Double(-14, -40, 28, 310);
    }



    protected void rotation(Point2D newPoint) {
        Point2D point = this.inverseOfAPoint(newPoint);
        double ang = Math.atan2(point.getY(), point.getX());

        anglechange = anglechange + ang-Math.toRadians(90);
        if(anglechange >= -0.75 && anglechange + this.parent.anglechange <= 0.81) {
            transform.rotate(ang-Math.toRadians(90));
        }
        else {
            anglechange = anglechange - (ang-Math.toRadians(90));
        }
        
    }

    protected void scaling(Point2D newPoint, Point2D oldPoint) {
        double x_dif = newPoint.getX() - oldPoint.getX();
        double y_dif = newPoint.getY() - oldPoint.getY();
        if( x_dif > 0 && numberofscales < 35) {
            transform.scale(1,1.01);
            numberofscales = numberofscales + 1;
        }
        else if (x_dif < 0 && numberofscales >= 1) {
            transform.scale(1,1/(1.01));
            numberofscales = numberofscales - 1;
        }  
    }    
    protected void magneting() {}

    protected void init() {
        transform.setToIdentity();
        transform(AffineTransform.getTranslateInstance(-25, 210));
        transform(AffineTransform.getRotateInstance(Math.PI/ 180*75));
    }

    
}