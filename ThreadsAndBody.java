
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
public class ThreadsAndBody extends Sprite {

    private Rectangle2D rect = null;
    private Polygon poly1 = null;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public ThreadsAndBody() {
        super();
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
        return poly1.contains(newPoint) || rect.contains(newPoint) ; 
    }

    protected void drawSprite(Graphics2D g) {
        final float d1[] =  {12.0f};
        final BasicStroke da =   new BasicStroke( 4.0f , BasicStroke.CAP_BUTT,   BasicStroke.JOIN_MITER,  10.0f, d1  , 0.0f);
        
        Graphics2D g2 = (Graphics2D)g.create();
        
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.gray);
        g2.fillRect(0, 0, 210, 40);
        
        int[] x = new int[]{20 ,20  ,-60   ,-60  ,-60     ,145   ,200   ,200, 180, 180};
        int[] y = new int[]{0  ,-20  ,-20  ,-60  ,-120   ,-120  ,-60  ,-20, -20, 0};
        g2.setColor(Color.orange);
        g2.fillPolygon (x, y, x.length);
        g2.setColor(Color.black);
        g2.drawPolygon(x, y, x.length);
        poly1 = new Polygon(x, y, x.length);

        x = new int[]{120     ,170   , 120,   120};
        y = new int[]{-100   ,-40  ,  -40,   -100 };
        g2.setColor(Color.white);
        g2.fillPolygon (x, y, x.length);
        g2.setColor(Color.black);
        g2.drawPolygon(x, y, x.length);
        
        
        g2.setStroke(da);
        g2.drawRect(0, 0, 210, 40);
        rect = new Rectangle2D.Double(0, 0, 210, 40);

    }
    protected void scaling(Point2D newPoint, Point2D oldPoint) {
        double x_dif = newPoint.getX() - oldPoint.getX();
        double y_dif = newPoint.getY() - oldPoint.getY();
        if( x_dif > 0) {
            transform.scale(1.01,1.01);
        }
        else if (x_dif < 0) {
            transform.scale(1/(1.01),1/(1.01));
        }  
    }

    protected void rotation(Point2D newPoint) {}
    protected void magneting() {}

    protected void init() {
        transform.setToIdentity();
        transform(AffineTransform.getTranslateInstance(80, 460));
    }
    
}