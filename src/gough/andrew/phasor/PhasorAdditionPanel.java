package gough.andrew.phasor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

	import javax.swing.BorderFactory;
import javax.swing.JPanel;

	public class PhasorAdditionPanel extends JPanel {
		//Represents the Phasor and draws in real time:
		//E = E0 sin(wt+theta+currentPhase)
		/**
		 * 
		 */
		
		private ActionListener owner;		
		private ArrayList<PhasorPanel> allPanels;
		private double maxE0;

		private static final long serialVersionUID = 2616406262841144238L;

		public PhasorAdditionPanel(ActionListener owner){
			this.owner = owner;
			allPanels = new ArrayList<PhasorPanel>();
		}
		




		public double getMaxE0(){
			return maxE0;
		}
		
		public void updateMaxE0(){
			int i = 0;
			maxE0 = 0D;
			while(i<allPanels.size()){
				maxE0 = maxE0 + allPanels.get(i).getE0();
				i++;
			}
		//	System.out.println("maxE0");
		}



		public boolean progressTime(){
			return true;
		}
		
		public void updatePhasors(PhasorPanel[] input){
			allPanels.clear();
			allPanels= new ArrayList<PhasorPanel>(Arrays.asList(input));
			updateMaxE0();
		}
		

		private void drawVectors(Graphics g, int startPointX,int startPointY, int smallestSide){
			// This smallest side should be = to max E0
			int i = 0;				
			updateMaxE0();
			while(i<allPanels.size()){
				//System.out.println(i);
				//(int) (Math.cos(currentPhase)*((smallestSide-1)/2))+getSize().width/2
				//System.out.println((int)((smallestSide/2)*(allPanels.get(i).getVector().getXVector()*allPanels.get(i).getE0())/maxE0));
				g.drawLine(startPointX, startPointY,startPointX + (int)((smallestSide/2)*(allPanels.get(i).getVector().getXVector()*allPanels.get(i).getE0())/maxE0),startPointY+(int)((smallestSide/2)*(allPanels.get(i).getVector().getYVector()*allPanels.get(i).getE0())/maxE0));
				startPointX = startPointX + (int)((smallestSide/2)*(allPanels.get(i).getVector().getXVector()*allPanels.get(i).getE0())/maxE0);
				//System.out.println(startPointX);
				startPointY = startPointY + (int)((smallestSide/2)*(allPanels.get(i).getVector().getYVector()*allPanels.get(i).getE0())/maxE0);
			i++;
			}
			
			return;
		}
		
		protected void  paintComponent(Graphics g){
//			super.printComponent(g);
			int xOffset= 0;
			int yOffset = 0;
			if(this.getSize().width>this.getSize().height){
				xOffset = this.getSize().width-this.getSize().height;
			}else{
				yOffset = this.getSize().height-this.getSize().width;
			}
			
			int smallestSide = (Math.min(this.getSize().height, this.getSize().width));
			g.setColor(this.getBackground());
			
			g.fillRect(0, 0, getPreferredSize().width-1, getPreferredSize().height-1);
			g.setColor(Color.black);
			//Draws Vertical Line
			g.drawLine(getSize().width/2, 0, getSize().width/2, getSize().height);
			//Draws Horizontal Line
			g.drawLine(0, getSize().height/2, getSize().width, getSize().height/2);
			//Draws the Oval
			g.drawOval(xOffset/2, yOffset/2, smallestSide-1, smallestSide-1);
			//Draw the vector!
			//g.fillOval(getSize().width/2-(10),getSize().height/2-(10),20,20);
			drawVectors(g,getSize().width/2, getSize().height/2,smallestSide-1);
		}

	}


