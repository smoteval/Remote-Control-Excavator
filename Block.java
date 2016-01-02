
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
public class Block extends Sprite {

    private Rectangle2D rect = null;
    private Sprite magnet;

    /// Constructor
    public Block(Sprite m) {
        super();
        magnet = m;
        block =  true;
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


        g2.setColor(Color.darkGray);
        g2.fillRect(0,0,80,80);
        g2.setColor(Color.black);
        g2.drawRect(0,0,80,80);
        rect = new Rectangle2D.Double(0,0,80,80);       

    }

    
    protected void magneting() {
        if( parent != null  ) {
            magnet.children.remove(this);
            parent = null;
            transform = magnet.getFullTransform();
            this.transform(AffineTransform.getTranslateInstance(-40, -115));
            return;
        }
        /////        
        // transform.setToIdentity();
        // magnet.addChild(this);
        // this.transform(AffineTransform.getTranslateInstance(-40, -115));
        //////
        Point2D p1 = new Point2D.Double(0,0);
        Point2D p2 = new Point2D.Double(0,80);
        Point2D p3 = new Point2D.Double(80,0);
        Point2D p4 = new Point2D.Double(80,80);
        Point2D p5 = new Point2D.Double(40,40);
        Point2D p6 = new Point2D.Double(20,20);
        Point2D p7 = new Point2D.Double(60,60);
        AffineTransform fullTransform = this.getFullTransform();
        fullTransform.transform(p1, p1); 
        fullTransform.transform(p2, p2);
        fullTransform.transform(p3, p3); 
        fullTransform.transform(p4, p4);  
        fullTransform.transform(p5, p5);
        fullTransform.transform(p6, p6);
        fullTransform.transform(p7, p7);
        if( magnet.pointInside(p1) || magnet.pointInside(p2) || magnet.pointInside(p3) || magnet.pointInside(p4) || magnet.pointInside(p5)|| magnet.pointInside(p6)|| magnet.pointInside(p7)) {
            transform.setToIdentity();
            magnet.addChild(this);
            this.transform(AffineTransform.getTranslateInstance(-40, -115));
        } 
    }


    protected void rotation(Point2D newPoint) {}

    protected void scaling(Point2D newPoint, Point2D oldPoint) {}    

    protected void init() {
        if( parent != null  ) {
            magnet.children.remove(this);
            parent = null;
        }
        transform.setToIdentity();
        transform(AffineTransform.getTranslateInstance(660, 430));
    }


    
}