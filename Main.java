
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Graphics;

public class Main {

	/**
	 * Test code.
	 */
	public static void main(String[] args) {		
        
		SpriteCanvas canvas = new SpriteCanvas();
		Sprite s1 = Main.makeSprite();
		Sprite s2 = Main.makeBlock(s1.specialchild);
		canvas.addSprite(s2);
		canvas.addSprite(s1);

		JFrame f = new JFrame("Assignment 2");
		f.setJMenuBar(Main.makeMenuBar(canvas));
		f.getContentPane().add(canvas);
		f.getContentPane().setLayout(new GridLayout(1, 1));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 600);
		f.setVisible(true);
	}
	
	/* Make a sample sprite for testing purposes. */
	private static Sprite makeSprite() {

		Sprite firstArm = new ThreadsAndBody();
		Sprite secondArm = new Boom();
		Sprite thirdArm = new Arm();
		Sprite fourthArm = new Bucket();
		//Sprite block = new Block(fourthArm);
		//block.transform(AffineTransform.getTranslateInstance(400, 400));

		firstArm.transform(AffineTransform.getTranslateInstance(80, 460));
		secondArm.transform(AffineTransform.getTranslateInstance(160,-80 ));
		secondArm.transform(AffineTransform.getRotateInstance(-Math.PI/ 180*140));
		thirdArm.transform(AffineTransform.getTranslateInstance(-25, 210));
		thirdArm.transform(AffineTransform.getRotateInstance(Math.PI/ 180*75));
		fourthArm.transform(AffineTransform.getTranslateInstance(-3, 255));
		fourthArm.transform(AffineTransform.getRotateInstance(Math.PI/ 180*180));

		firstArm.addChild(secondArm);
		secondArm.addChild(thirdArm);
		thirdArm.addChild(fourthArm);
		firstArm.specialchild = fourthArm;
		
		return firstArm;
	}
	private static Sprite makeBlock(Sprite mag) {
		Sprite block = new Block(mag);
		block.transform(AffineTransform.getTranslateInstance(660, 430));
		return block;
	}


	/* Menu with recording and playback. */
	private static JMenuBar makeMenuBar(final SpriteCanvas canvas) {
		JMenuBar mbar = new JMenuBar();

		JMenu script = new JMenu("Scripting");
		final JMenuItem record = new JMenuItem("Start recording");
		final JMenuItem play = new JMenuItem("Start script");

		JMenu file = new JMenu("File");
		final JMenuItem reset = new JMenuItem("Reset");
		final JMenuItem quit = new JMenuItem("Quit");


		script.add(record);
		script.add(play);

		file.add(reset);
		file.add(quit);

		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.initia();
			}
		});	

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});			

		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (record.getText().equals("Start recording")) {
					record.setText("Stop recording");
					canvas.startRecording();
				} else if (record.getText().equals("Stop recording")) {
					record.setText("Start recording");
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (play.getText().equals("Start script")) {
					play.setText("Stop script");
					record.setEnabled(false);
					canvas.startDemo();
				} else if (play.getText().equals("Stop script")) {
					play.setText("Start script");
					record.setEnabled(true);
					canvas.stopRecording();
				} else {
					assert false;
				}
			}
		});
        
        mbar.add(file);
		mbar.add(script);
		return mbar;
	}




}
