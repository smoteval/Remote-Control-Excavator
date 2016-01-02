
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

/**
 * A building block for creating your own shapes that can be
 * transformed and that can respond to input. This class is
 * provided as an example; you will likely need to modify it
 * to meet the assignment requirements.
 * 
 * Michael Terry
 */
public abstract class Sprite {
    
    /**
     * Tracks our current interaction mode after a mouse-down
     */
    protected enum InteractionMode {
        IDLE,
        DRAGGING,
        SCALING,
        ROTATING,
        MAGNET
    }

    protected     Vector<Sprite>      children            = new Vector<Sprite>();     // Holds all of our children
    protected     Sprite              parent              = null;                     // Pointer to our parent
    protected     AffineTransform     transform           = new AffineTransform();    // Our transformation matrix

    protected   Point2D             lastPoint           = null;                    // Last mouse point
    protected   InteractionMode     interactionMode     = InteractionMode.IDLE;     // Current interaction mode
    protected   boolean             rotatable           = false;
    protected   double              anglechange         =  0;
    protected   boolean             block               = false;
    public      Sprite              specialchild        = null;
    
    
    public Sprite() {
        ; // no-op
    }
    
    public Sprite(Sprite parent) {
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(Sprite s) {
        children.add(s);
        s.setParent(this);
    }
    public Sprite getParent() {
        return parent;
    }
    private void setParent(Sprite s) {
        this.parent = s;
    }

    /**
     * Test whether a point, in world coordinates, is within our sprite.
     */
    public abstract boolean pointInside(Point2D p);

    protected abstract void init();
    /**
     * Handles a mouse down event, assuming that the event has already
     * been tested to ensure the mouse point is within our sprite.
     */
    protected void handleMouseDownEvent(MouseEvent e) {
        lastPoint = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON1) {
            if( block ) {
                this.magneting();    
            }
            else if( rotatable ) {
                interactionMode = InteractionMode.ROTATING;
            }
            else {
                interactionMode = InteractionMode.DRAGGING;
            }
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            interactionMode = InteractionMode.SCALING;
        }
        // Handle rotation, scaling mode depending on input
    }

    /**
     * Handle mouse drag event, with the assumption that we have already
     * been "selected" as the sprite to interact with.
     * This is a very simple method that only works because we
     * assume that the coordinate system has not been modified
     * by scales or rotations. You will need to modify this method
     * appropriately so it can handle arbitrary transformations.
     */
    protected void handleMouseDragEvent(MouseEvent e) {
        
        Point2D oldPoint = lastPoint;
        Point2D newPoint = e.getPoint();
        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
                double x_diff = newPoint.getX() - oldPoint.getX();
                double y_diff = newPoint.getY() - oldPoint.getY();
                transform.translate(x_diff, y_diff);
                break;
            case ROTATING:          
                this.rotation(newPoint);
                break;
            case SCALING:        
                this.scaling(newPoint,oldPoint);
                break;
            case MAGNET:
                this.magneting();    
                break;    
                
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
    }
    
    protected void handleMouseUp(MouseEvent e) {
        interactionMode = InteractionMode.IDLE;
        // Do any other interaction handling necessary here
    }
    
    /**
     * Locates the sprite that was hit by the given event.
     * You *may* need to modify this method, depending on
     * how you modify other parts of the class.
     * 
     * @return The sprite that was hit, or null if no sprite was hit
     */
    public Sprite getSpriteHit(MouseEvent e) {
        for (Sprite sprite : children) {
            Sprite s = sprite.getSpriteHit(e);
            if (s != null) {
                return s;
            }
        }
        if (this.pointInside(e.getPoint())) {
            return this;
        }
        return null;
    }
    
    /*
     * Important note: How transforms are handled here are only an example. You will
     * likely need to modify this code for it to work for your assignment.
     */
    
    /**
     * Returns the full transform to this object from the root
     */
    public AffineTransform getFullTransform() {
        AffineTransform returnTransform = new AffineTransform();
        Sprite curSprite = this;
        while (curSprite != null) {
            returnTransform.preConcatenate(curSprite.getLocalTransform());
            curSprite = curSprite.getParent();
        }
        return returnTransform;
    }

    /**
     * Returns our local transform
     */
    public AffineTransform getLocalTransform() {
        return (AffineTransform)transform.clone();
    }
    
    /**
     * Performs an arbitrary transform on this sprite
     */
    public void transform(AffineTransform t) {
        transform.concatenate(t);
    }

    /**
     * Draws the sprite. This method will call drawSprite after
     * the transform has been set up for this sprite.
     */
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();

        //Fixing the bug by multiplying the affinetransform associated to g to our sprite fulltransformation matrix
        AffineTransform newTransform = this.getFullTransform();
        newTransform.preConcatenate(oldTransform);
        // Set to our transform
        g.setTransform(newTransform);
        
        // Draw the sprite (delegated to sub-classes)
        this.drawSprite(g);
        
        // Restore original transform
        g.setTransform(oldTransform);

        // Draw children
        for (Sprite sprite : children) {
            sprite.draw(g);
        }
    }
    
    /**
     * The method that actually does the sprite drawing. This method
     * is called after the transform has been set up in the draw() method.
     * Sub-classes should override this method to perform the drawing.
     */
    protected abstract void drawSprite(Graphics2D g);
    protected abstract void rotation(Point2D newPoint);
    protected abstract void scaling(Point2D newPoint, Point2D oldPoint);
    protected abstract void magneting();

    public Point2D inverseOfAPoint(Point2D newPoint) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException s) {
            s.printStackTrace();
        }
        Point2D point = (Point2D)newPoint.clone();
        inverseTransform.transform(point, point);
        return point;
    }
}
