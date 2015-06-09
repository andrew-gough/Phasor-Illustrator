package gough.andrew.phasor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class PhasorMain implements MouseListener, ActionListener {
	final double TIMEDELAY = 0.025; // This is the delay between updates
	PhasorMain current = this;
	JFrame frame;
	PhasorAdditionPanel totaller;
	JScrollPane scroll;
	JPanel storagePanel;
	Timer scheduler;

	public PhasorMain() {
		makeFrame();
		scheduler = new Timer((int) (TIMEDELAY * 1000), this);
		scheduler.start();

	}

	private void updatePhasors() {
		// System.out.println("Updated!");
		Component[] inputs = storagePanel.getComponents();
		PhasorPanel current;
		int i = 0;
		while (i < inputs.length) {
			current = (PhasorPanel) inputs[i];
			current.progressTime(TIMEDELAY);
			i++;
		}
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	private void resetPhases(){
		Component[] inputs = storagePanel.getComponents();
		PhasorPanel current;
		int i = 0;
		while (i < inputs.length) {
			current = (PhasorPanel) inputs[i];
			current.resetPhase();
			i++;
		}
		SwingUtilities.updateComponentTreeUI(frame);	
	}
	
	private PhasorPanel[] componentsToPhasors(Component[] input){
		PhasorPanel[] temp = new PhasorPanel[input.length];
		int i = 0;
		while(i<temp.length){
			temp[i] = (PhasorPanel)input[i];
			i++;
		}
		return temp;
	}

	// ---- Swing stuff to build the frame and all its components and menus ----

	/**
	 * Create the Swing frame and its content.
	 */
	private void makeFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame = new JFrame("Phasor Illustration");
				JPanel contentPane = (JPanel) frame.getContentPane();
				contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));

				contentPane.setLayout(new GridBagLayout());

				GridBagConstraints c = new GridBagConstraints();

				totaller = new PhasorAdditionPanel(current);
				totaller.addMouseListener(current);
				c.gridwidth = 3;
				c.gridheight = 1;
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 1;
				c.weighty = 0.75;
				c.gridx = 0;
				c.gridy = 0;
				contentPane.add(totaller, c);

				storagePanel = new JPanel();
				storagePanel.setLayout(new BoxLayout(storagePanel,
						BoxLayout.X_AXIS));
				storagePanel.setBorder(new EmptyBorder(0, 12, 0, 12));

				scroll = new JScrollPane(storagePanel);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				c.gridwidth = 3;
				c.gridheight = 1;
				c.fill = GridBagConstraints.BOTH;
				c.weightx = 1;
				c.weighty = 0.33;
				c.gridx = 0;
				c.gridy = 2;
				contentPane.add(scroll, c);

				makeMenuBar(frame);
				
				frame.pack();
				frame.setSize(new Dimension(400, 400));
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				// place the frame at the center of the screen and show
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height
						/ 2 - frame.getHeight() / 2);
				frame.setVisible(true);
			}
		});

	}
	
	 private void makeMenuBar(JFrame frame)
     {
             final int SHORTCUT_MASK =
                             Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

             JMenuBar menubar = new JMenuBar();
             frame.setJMenuBar(menubar);

             JMenu menu;
             JMenuItem item;
             
             


             // create the File menu
             menu = new JMenu("Actions");
             menubar.add(menu);
             
             item = new JMenuItem("Start Simulation");
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	scheduler.start();
                     }
             });
             menu.add(item);
             
             item = new JMenuItem("Stop Simulation");
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    		scheduler.stop();
                     }
             });
             menu.add(item);
             
             menu.addSeparator();

             item = new JMenuItem("Synchronize Phases");
             item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	 resetPhases();
                     }
             });
             menu.add(item);
             
             item = new JMenuItem("Add Phasor");
             item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	 addPhaser();
                     }
             });
             menu.add(item);
             
             item = new JMenuItem("Reset Simulation");
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	 reset();
                     }
             });
             menu.add(item);
             
             menu = new JMenu("Info");
             menubar.add(menu);
             
             item = new JMenuItem("Information");
             item.addActionListener(new ActionListener() {
                     public void actionPerformed(ActionEvent e) { 
                    	 showInfo();
                     }
             });
             menu.add(item);

             
     }
	 
	private void showInfo(){
		//custom title, no icon
		System.out.println("Clicked!");
		JOptionPane.showMessageDialog(frame,
		    "Version 1.0 - Andrew Gough",
		    "Information:",
		    JOptionPane.PLAIN_MESSAGE);
	}
	 
	private void reset(){
		storagePanel.removeAll();				
		totaller.updatePhasors(componentsToPhasors(storagePanel.getComponents()));
		scheduler.stop();
		SwingUtilities.updateComponentTreeUI(frame);
	}
	 
	private void addPhaser(){
		storagePanel.add(new PhasorPanel("input Info", this));
		totaller.updatePhasors(componentsToPhasors(storagePanel.getComponents()));
		SwingUtilities.updateComponentTreeUI(frame);
	}

	@Override
	public void mouseClicked(MouseEvent ae) {
		// if(ae.getSource()==totaller){
		// System.out.println("Clicked!");
		// storagePanel.add(new PhasorPanel());
		// SwingUtilities.updateComponentTreeUI(frame);
		//
		// }
		//
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent ae) {
		if (ae.getSource() == totaller) {
			storagePanel.add(new PhasorPanel("input Info", this));
			totaller.updatePhasors(componentsToPhasors(storagePanel.getComponents()));
			SwingUtilities.updateComponentTreeUI(frame);

		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == scheduler) {
			updatePhasors();
		}
		if (ae.getSource() instanceof PhasorPanel) {
			if (ae.getActionCommand().equals("stopTimer")) {
				// stop the Timer;
				scheduler.stop();
			}

			if (ae.getActionCommand().equals("startTimer")) {
				// start the Timer;
				scheduler.start();
			}
			
			if (ae.getActionCommand().equals("deletePanel")) {
				//take the source out of storagePanel
				storagePanel.remove((PhasorPanel)ae.getSource());
				totaller.updatePhasors(componentsToPhasors(storagePanel.getComponents()));
				if(storagePanel.getComponentCount()!= 0){
					scheduler.start();
				}
				SwingUtilities.updateComponentTreeUI(frame);
			}
		}

	}
}
