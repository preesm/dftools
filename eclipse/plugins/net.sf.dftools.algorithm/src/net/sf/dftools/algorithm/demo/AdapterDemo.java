package net.sf.dftools.algorithm.demo;

import javax.swing.JFrame;

import org.jgrapht.demo.JGraphAdapterDemo;

/**
 * Calling JGraphT demo to check import.
 * 
 * @author Maxime Pelcat
 * @since Fev, 25, 2008
 */
public class AdapterDemo {

	/**
	 * Calling JGraphT test applet.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		JGraphAdapterDemo applet = new JGraphAdapterDemo();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("Calling JGraphT demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
