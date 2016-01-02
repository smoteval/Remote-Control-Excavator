
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;



/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Michael Terry
 */
public class Boom extends Sprite {

    private Polygon poly = null;

    /// Constructor
    public Boom() {
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
        return poly.contains(newPoint) ; 
    }

    protected void drawSprite(Graphics2D g) {
        
        Graphics2D g2 = (Graphics2D)g.create();


        int[] x = new int[]{16,-16,-16 ,-50  ,-18  ,16     };
        int[] y = new int[]{0 ,0  ,120 ,250,250 ,120   };
        g2.setColor(Color.orange);
        g2.fillPolygon (x, y, x.length);
        g2.setColor(Color.black);
        g2.drawPolygon(x, y, x.length);
        g2.setColor(Color.darkGray);
        g2.fillOval(-6, 0, 12, 10);
        poly = new Polygon(x, y, x.length);

    }

    protected void rotation(Point2D newPoint) {
        Point2D point = inverseOfAPoint(newPoint);
        double ang = Math.atan2(point.getY(), point.getX());
        anglechange = anglechange + ang-Math.toRadians(90);
        //System.out.println(anglechange);
        if(anglechange <= 0.43 && anglechange >= -0.53 ){
            transform.rotate(ang-Math.toRadians(90));
        }
        else {
            anglechange = anglechange - (ang-Math.toRadians(90));
        }
    }

    protected void scaling(Point2D newPoint, Point2D oldPoint) {}
    protected void magneting() {}

    protected void init() {
        this.transform.setToIdentity();
        transform(AffineTransform.getTranslateInstance(160,-80 ));
        transform(AffineTransform.getRotateInstance(-Math.PI/ 180*140));
    }
    
}