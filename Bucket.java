
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
//import java.lang.Math.abs;



/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry
 */
public class Bucket extends Sprite {

    private Rectangle2D rect = null;
    

    /// Constructor
    public Bucket() {
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
        return rect.contains(newPoint); 
    }

    protected void drawSprite(Graphics2D g) {
        
        Graphics2D g2 = (Graphics2D)g.create();


        g2.setColor(Color.orange);
        g2.fillRect(-45,-35,90,40);
        g2.setColor(Color.black);
        g2.drawRect(-45,-35,90,40);
        g2.setColor(Color.darkGray);
        g2.fillOval(-6, -6, 12, 12);
        rect = new Rectangle2D.Double(-45,-35,90,40);       

    }



    protected void rotation(Point2D newPoint) {
        Point2D point = inverseOfAPoint(newPoint);
        double ang = Math.atan2(point.getY(), point.getX());
        //System.out.println(ang);

        anglechange = anglechange + ang-Math.toRadians(90)+Math.toRadians(180);
        if(anglechange >= -0.46 && anglechange <= 0.54) {
            transform.rotate( (ang-Math.toRadians(90))+Math.toRadians(180) );
        }
        else {
            anglechange = anglechange - (ang-Math.toRadians(90)+Math.toRadians(180));
        }
        
    }

    protected void scaling(Point2D newPoint, Point2D oldPoint) {} 
    protected void magneting() {}



    protected void init() {
        transform.setToIdentity();
        transform(AffineTransform.getTranslateInstance(-3, 255));
        transform(AffineTransform.getRotateInstance(Math.PI/ 180*180));
    }



    
}