package gough.andrew.phasor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class PhasorPanel extends JPanel implements ActionListener,MouseListener,WindowListener {
	//Represents the Phasor and draws in real time:
	//E = E0 sin(wt+theta+currentPhase)
	/**
	 * 
	 */
	private JFrame frame1;
	private JTextField e0Input;
	private JTextField omegaInput;
	private JTextField thetaInput;
	private JButton input;
	private JButton defaultInfo;
	private JButton deletePanel;
	private PhasorPanel current = this;
	
	
	protected Dimension defaultDim = new Dimension(200,200);
	protected double e0 = 0D;
	protected double omega;
	protected double theta;
	protected double currentPhase;
	private ActionListener owner;

	private static final long serialVersionUID = 2616406262841144238L;

	public PhasorPanel(){
		super();
		setAllVariables(0,0,0);
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public PhasorPanel(String input,ActionListener owner){
		super();
		super.addMouseListener(this);
		setAllVariables(0,0,0);
		this.owner = owner;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		if(input.equals("input Info")){
			popUpInfo();
		}else{
			defaultDim = new Dimension(50,100);
		}
	}


	public PhasorPanel(double magnitude, double omegaInput, double thetaInput){
		super();
		setAllVariables(magnitude,omegaInput,thetaInput);
	}

	public PhasorPanel(double magnitude,double omegaInput){
		super();
		setAllVariables(magnitude,omegaInput,0);
	}

	public void setAllVariables(double inputE0,double omegaInput, double thetaInput){
		e0 = inputE0;
		omega = omegaInput;
		theta = thetaInput;
		currentPhase = theta;
	}

	public boolean setE0(double input){
		e0 = input;
		return true;
	}

	public boolean setOmega(double input){
		omega = input;
		return true;
	}

	public boolean setTheta(double input){
		theta = input;
		return true;
	}

	public double getE0(){
		return e0;
	}

	public double getOmega(){
		return omega;
	}

	public double getTheta(){
		return theta;
	}

	public boolean progressTime(double deltaTime){
		currentPhase = (currentPhase + (deltaTime*omega*Math.PI))%(Math.PI*2);
	//	repaint();
		return true;
	}

	public Vector getVector(){
		return new Vector(Math.cos(currentPhase),Math.sin(currentPhase));
	}
	
	public void resetPhase(){
		currentPhase = theta;
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getPreferredSize() {
		//This method makes sure the panel always remains this size
		return defaultDim;
	}
	
	
	
	protected void  paintComponent(Graphics g){
		//super.printComponent(g);
		int xOffset = (this.getSize().width/2)-(getPreferredSize().width/2);
		int smallestSide = Math.min(this.getSize().height, this.getSize().width);
		g.setColor(this.getBackground());
		g.fillRect(0, 0, getPreferredSize().width-1, getPreferredSize().height-1);
		g.setColor(Color.black);
		//Draws Vertical Line
		g.drawLine(getSize().width/2, 0, getSize().width/2, getSize().height);
		//Draws Horizontal Line
		g.drawLine(0, getSize().height/2, getSize().width, getSize().height/2);
		//Draws the Oval
		g.drawOval(xOffset, 0, smallestSide-1, smallestSide-1);
		//Draw the vector!
		//System.out.println(currentPhase);
		//System.out.println("x : "+(int) (Math.cos(currentPhase)*((smallestSide-1)/2))+ " y: "+(int) (Math.sin(currentPhase)*((smallestSide-1)/2)));
		g.drawLine(getSize().width/2, getSize().height/2,(int) (Math.cos(currentPhase)*((smallestSide-1)/2))+getSize().width/2,(int) (Math.sin(currentPhase)*((smallestSide-1)/2))+getSize().width/2);
	}
	
	protected void popUpInfo(){
		owner.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"stopTimer"));
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	 frame1 = new JFrame("Please Enter The Parameters for the Wave");
	        	 frame1.addWindowListener(current);
	             JPanel contentPane = (JPanel)frame1.getContentPane();
	             contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
	             
	             contentPane.setLayout(new GridBagLayout());

	             GridBagConstraints c = new GridBagConstraints();
	             
	             JLabel labelE0 = new JLabel("Amplitude:");
	             JLabel labelOmega = new JLabel("Omega(Radians):");
	             JLabel labelPhaseTheta = new JLabel("Theta(Radians)");
	             
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0;
	             c.weighty = 0.3;
	             c.gridx = 0;
	             c.gridy = 0;
	             contentPane.add(labelE0,c);
	             
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0;
	             c.weighty = 0.3;
	             c.gridx = 0;
	             c.gridy = 1;
	             contentPane.add(labelOmega,c);
	             
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0;
	             c.weighty = 0.3;
	             c.gridx = 0;
	             c.gridy = 2;
	             contentPane.add(labelPhaseTheta,c);
	             
	             e0Input = new JTextField();
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.6;
	             c.weighty = 0.3;
	             c.gridx = 3;
	             c.gridy = 0;
	             contentPane.add(e0Input,c);
	             
	             omegaInput = new JTextField();
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.6;
	             c.weighty = 0.3;
	             c.gridx = 3;
	             c.gridy = 1;
	             contentPane.add(omegaInput,c);
	             
	             thetaInput = new JTextField();
	             c.gridwidth = 3;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.6;
	             c.weighty = 0.3;
	             c.gridx = 3;
	             c.gridy = 2;
	             contentPane.add(thetaInput,c);
	             
//	         	private JButton input;
//	        	private JButton defaultInfo;
//	        	private JButton deletePanel;
	             
	             input = new JButton("Input Values");
	             input.addActionListener(current);
	             c.gridwidth = 2;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.2;
	             c.weighty = 0.3;
	             c.gridx = 0;
	             c.gridy = 3;
	             contentPane.add(input,c);
	             
	             defaultInfo = new JButton("Reset to Default");
	             defaultInfo.addActionListener(current);
	             c.gridwidth = 2;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.2;
	             c.weighty = 0.3;
	             c.gridx = 2;
	             c.gridy = 3;
	             contentPane.add(defaultInfo,c);
	             
	             deletePanel = new JButton("Delete this Panel");
	             deletePanel.addActionListener(current);
	             c.gridwidth = 2;
	             c.gridheight = 1;
	             c.fill = GridBagConstraints.BOTH;
	             c.weightx = 0.2;
	             c.weighty = 0.3;
	             c.gridx = 4;
	             c.gridy = 3;
	             contentPane.add(deletePanel,c);
	             
	             if(e0!= 0){
	            	 e0Input.setText(String.valueOf(e0));
	             }else{
	            	 e0Input.setText("1");
	             }
	             
	             if(omega!= 0){
	            	 omegaInput.setText(String.valueOf(omega));
	             }else{
	            	 omegaInput.setText("1");
	             }
	             
	             if(theta!= 0){
	            	 thetaInput.setText(String.valueOf(theta));
	             }else{
	            	 thetaInput.setText("0");
	             }

	             frame1.pack();
	             //frame1.setSize(new Dimension(400,400));
	             frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	             // place the frame at the center of the screen and show
	             Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	             frame1.setLocation(d.width/2 - frame1.getWidth()/2, d.height/2 - frame1.getHeight()/2);
	             frame1.setVisible(true);     
	        }
	    });
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==input){
			try{
			getInputs();
			}catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(frame1,
					    "Invalid Input!",
					    "Invalid Input",
					    JOptionPane.WARNING_MESSAGE);
						return;
			}
			owner.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"startTimer"));
			frame1.dispose();
		
		}
		
		if(ae.getSource()==defaultInfo){
			resetInputs();
		}
		
		if(ae.getSource() ==deletePanel){
			owner.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"deletePanel"));
			frame1.dispose();
		}
		
	}
	
	private void getInputs() throws NumberFormatException{
	
		setAllVariables(Double.parseDouble(e0Input.getText()),Double.parseDouble(omegaInput.getText()),Double.parseDouble(thetaInput.getText()));
	
	}
	
	private void resetInputs(){
		e0Input.setText("1");
		omegaInput.setText("1");
		thetaInput.setText("0");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mousePressed(MouseEvent me) {
		if(me.getSource() instanceof PhasorPanel ){
		popUpInfo();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent we) {
		owner.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"deletePanel"));
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
