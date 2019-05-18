package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Sameer Mithani
 * ssm2756
 * 16345
 * Adri Dutta
 * ad38742
 * 16360
 * Slip days used: <0>
 * Fall 2018
 */
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import assignment5.Critter;
import assignment5.InvalidCritterException;
import assignment5.Params;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.*;
import javafx.animation.*;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR,
		HEXAGON
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {
		int temp_x;
		int temp_y;
		if(moved==1) {					//based on old position of critter
			temp_x = old_x;
			temp_y = old_y;
		} else {
			temp_x = this.getX();
			temp_y = this.getY();
		}
		
		if(steps) {
			switch(direction) {
			case 0:	temp_x = (temp_x+2) % Params.world_width;
					if (temp_x <0) {temp_x *= -1;}
					break;
			case 1: temp_x = (temp_x+2) % Params.world_width;
					temp_y = (temp_y-2) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
			
			case 2: temp_y = (temp_y-2) % Params.world_height;
					if (temp_y <0) {temp_y *= -1;}
					break;
				
				
			case 3: temp_x = (temp_x-2) % Params.world_width;
					temp_y = (temp_y-2) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
				
			case 4: temp_x = (temp_x-2) % Params.world_width;
					if (temp_x <0) {temp_x *= -1;}
					break;
				
				
			case 5: temp_x = (temp_x-2) % Params.world_width;
					temp_y = (temp_y+2) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
				
			case 6: temp_y = (temp_y+2) % Params.world_height;
					if (temp_y <0) {temp_y *= -1;}
					break;
				
				
			case 7:	temp_x = (temp_x+2) % Params.world_width;
					temp_y = (temp_y+2) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;			
			
			}
		} else {
			switch(direction) {
			case 0:	temp_x = (temp_x+1) % Params.world_width;
					if (temp_x <0) {temp_x *= -1;}
					break;
			case 1: temp_x = (temp_x+1) % Params.world_width;
					temp_y = (temp_y-1) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
			
			case 2: temp_y = (temp_y-1) % Params.world_height;
					if (temp_y <0) {temp_y *= -1;}
					break;
				
				
			case 3: temp_x = (temp_x-1) % Params.world_width;
					temp_y = (temp_y-1) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
				
			case 4: temp_x = (temp_x-1) % Params.world_width;
					if (temp_x <0) {temp_x *= -1;}
					break;
				
				
			case 5: temp_x = (temp_x-1) % Params.world_width;
					temp_y = (temp_y+1) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;
				
			case 6: temp_y = (temp_y+1) % Params.world_height;
					if (temp_y <0) {temp_y *= -1;}
					break;
				
				
			case 7:	temp_x = (temp_x+1) % Params.world_width;
					temp_y = (temp_y+1) % Params.world_height;
					if (temp_x <0) {temp_x *= -1;}
					if (temp_y <0) {temp_y *= -1;}
					break;			
			
			}
		}
		
		lookEnergy();
		
		
		
		for(Critter crit: population) {
			
			if (fight == 1) {
				if(crit.old_x == temp_x && crit.old_y == temp_y) {
					return crit.toString();
				}
			}
			else
			{
				if(crit.x_coord == temp_x && crit.y_coord == temp_y) {
					return crit.toString();
				}
			}
		}
		return null;
	}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private int moved = 0;
	private int fight = 0;
	private int old_x;
	private int old_y;

	private void lookEnergy() {
		energy -= Params.look_energy_cost;
	}
	
	private int getX()
	{
		return this.x_coord;
	}
	
	private int getY()
	{
		return this.y_coord;
	}
	
	private final void restEnergy()         //remove for rest energy
	{
		energy-=Params.rest_energy_cost;
	}
	
	private final void moveReset()                        // reset move after timestep
	{
		moved = 0;
	}
	
	protected final void walk(int direction) {
		if (moved ==0 && fight ==0) {
			old_x = this.x_coord;
			old_y = this.y_coord;
			if (fight == 1)                  // if fight is invoked
			{
				int prevx = this.x_coord;      //in case we need to go back
				int prevy = this.y_coord;
				switch(direction) {
				case 0:		this.x_coord++;		//to the right
							if (x_coord == Params.world_width) {this.x_coord =0;}
							break;			
				case 1:		this.x_coord++;		//up right
							this.y_coord--;
							if (x_coord == Params.world_width) {this.x_coord =0;}
							if (y_coord < 0) {this.y_coord =Params.world_height-1;}
							break;
				case 2:		this.y_coord--;		//up
							if (y_coord < 0) {this.y_coord =Params.world_height-1;}
							break;
				case 3:		this.x_coord--;		//up left
							this.y_coord--;
							if (x_coord < 0) {this.x_coord =Params.world_width-1;}
							if (y_coord < 0) {this.y_coord =Params.world_height-1;}
							break;
				case 4:		this.x_coord--;		//left
							if (x_coord < 0) {this.x_coord =Params.world_width-1;}
							break;			
				case 5:		this.x_coord--;		//down left
							this.y_coord++;
							if (x_coord < 0) {this.x_coord =Params.world_width-1;}
							if (y_coord == Params.world_height) {this.y_coord =0;}
							break;
				case 6: 	this.y_coord++;		//down
							if (y_coord == Params.world_height) {this.y_coord =0;}
							break;
				case 7:		this.x_coord++;		//down right
							this.y_coord++;
							if (x_coord == Params.world_width) {this.x_coord =0;}
							if (y_coord == Params.world_height) {this.y_coord =0;}
							break;
			}//switch statement
			
				for (int i=0; i<population.size();i++)       // if same position then revert
				{
					if (population.get(i)!= this && population.get(i).getX()==this.getX() && population.get(i).getY()==this.getY())     // if it is not the same and x and y coords match then revert
					{
						this.x_coord = prevx;
						this.y_coord = prevy;
					}
				}
			}// fight if statement
			
			
			else {                                 // not a fight
			switch(direction) {
			case 0:		this.x_coord++;		//to the right
						if (x_coord == Params.world_width) {this.x_coord =0;}
						break;			
			case 1:		this.x_coord++;		//up right
						this.y_coord--;
						if (x_coord == Params.world_width) {this.x_coord =0;}
						if (y_coord < 0) {this.y_coord =Params.world_height-1;}
						break;
			case 2:		this.y_coord--;		//up
						if (y_coord < 0) {this.y_coord =Params.world_height-1;}
						break;
			case 3:		this.x_coord--;		//up left
						this.y_coord--;
						if (x_coord < 0) {this.x_coord =Params.world_width-1;}
						if (y_coord < 0) {this.y_coord =Params.world_height-1;}
						break;
			case 4:		this.x_coord--;		//left
						if (x_coord < 0) {this.x_coord =Params.world_width-1;}
						break;			
			case 5:		this.x_coord--;		//down left
						this.y_coord++;
						if (x_coord < 0) {this.x_coord =Params.world_width-1;}
						if (y_coord == Params.world_height) {this.y_coord =0;}
						break;
			case 6: 	this.y_coord++;		//down
						if (y_coord == Params.world_height) {this.y_coord =0;}
						break;
			case 7:		this.x_coord++;		//down right
						this.y_coord++;
						if (x_coord == Params.world_width) {this.x_coord =0;}
						if (y_coord == Params.world_height) {this.y_coord =0;}
						break;
			}//switch
		}//else statement
	}//moved if statement
	moved = 1;                                      // indicate when critter has moved
	this.energy -= Params.walk_energy_cost;			//use the energy
	}
	
	protected final void run(int direction) {
		if (moved ==0) {
			old_x = this.x_coord;
			old_y = this.y_coord;
			if (fight == 1)                   
			{
				int prevx = this.x_coord;         //in case we need to revert
				int prevy = this.y_coord;
				switch(direction) {
				case 0:		this.x_coord+=2;		//to the right
							if (x_coord == Params.world_width) {this.x_coord =0;}
							if (x_coord > Params.world_width) {this.x_coord =1;}
							break;
				case 1:		this.x_coord+=2;		//up right
							this.y_coord-=2;
							if (x_coord == Params.world_width) {this.x_coord =0;}
							if (y_coord == -1) {this.y_coord =Params.world_height-1;}
							if (x_coord > Params.world_width) {this.x_coord =1;}
							if (y_coord == -2) {this.y_coord =Params.world_height-2;}
							break;
				case 2:		this.y_coord-=2;		//up
							if (y_coord == -1) {this.y_coord =Params.world_height-1;}
							if (y_coord == -2) {this.y_coord =Params.world_height-2;}
							break;
							
				case 3:		this.x_coord-=2;		//up left
							this.y_coord-=2;
							if (x_coord == -1) {this.x_coord =Params.world_width-1;}
							if (y_coord == -1) {this.y_coord =Params.world_height-1;}
							if (x_coord == -2) {this.x_coord =Params.world_width-2;}
							if (y_coord == -2) {this.y_coord =Params.world_height-2;}
							break;
				case 4:		this.x_coord-=2;		//left
							if (x_coord == -1) {this.x_coord =Params.world_width-1;}
							if (x_coord == -2) {this.x_coord =Params.world_width-2;}
							break;			
				case 5:		this.x_coord-=2;		//down left
							this.y_coord+=2;
							if (x_coord == -1) {this.x_coord =Params.world_width-1;}
							if (y_coord == Params.world_height) {this.y_coord =0;}
							if (x_coord == -2) {this.x_coord =Params.world_width-2;}
							if (y_coord > Params.world_height) {this.y_coord =1;}
							break;
				case 6: 	this.y_coord+=2;		//down 
							if (y_coord == Params.world_height) {this.y_coord =0;}
							if (y_coord > Params.world_height) {this.y_coord =1;}
							break;
				case 7:		this.x_coord+=2;		//down right
							this.y_coord+=2;
							if (x_coord == Params.world_width) {this.x_coord =0;}
							if (y_coord == Params.world_height) {this.y_coord =0;}
							if (x_coord > Params.world_width) {this.x_coord =1;}
							if (y_coord > Params.world_height) {this.y_coord =1;}
							break;
				}// switch statement
				for (int i=0; i<population.size();i++)       // if same position then revert
				{
					if (population.get(i)!= this && population.get(i).getX()==this.getX() && population.get(i).getY()==this.getY())     // if it is not the same and x and y coords match then revert
					{
						this.x_coord = prevx;
						this.y_coord = prevy;
					}
				}
			}// fight if statement
	
			else {
			switch(direction) {
			case 0:		this.x_coord+=2;		//to the right
						if (x_coord == Params.world_width) {this.x_coord =0;}
						if (x_coord > Params.world_width) {this.x_coord =1;}
						break;
			case 1:		this.x_coord+=2;		//up right
						this.y_coord-=2;
						if (x_coord == Params.world_width) {this.x_coord =0;}
						if (y_coord == -1) {this.y_coord =Params.world_height-1;}
						if (x_coord > Params.world_width) {this.x_coord =1;}
						if (y_coord == -2) {this.y_coord =Params.world_height-2;}
						break;
			case 2:		this.y_coord-=2;		//up
						if (y_coord == -1) {this.y_coord =Params.world_height-1;}
						if (y_coord == -2) {this.y_coord =Params.world_height-2;}
						break;
						
			case 3:		this.x_coord-=2;		//up left
						this.y_coord-=2;
						if (x_coord == -1) {this.x_coord =Params.world_width-1;}
						if (y_coord == -1) {this.y_coord =Params.world_height-1;}
						if (x_coord == -2) {this.x_coord =Params.world_width-2;}
						if (y_coord == -2) {this.y_coord =Params.world_height-2;}
						break;
			case 4:		this.x_coord-=2;		//left
						if (x_coord == -1) {this.x_coord =Params.world_width-1;}
						if (x_coord == -2) {this.x_coord =Params.world_width-2;}
						break;			
			case 5:		this.x_coord-=2;		//down left
						this.y_coord+=2;
						if (x_coord == -1) {this.x_coord =Params.world_width-1;}
						if (y_coord == Params.world_height) {this.y_coord =0;}
						if (x_coord == -2) {this.x_coord =Params.world_width-2;}
						if (y_coord > Params.world_height) {this.y_coord =1;}
						break;
			case 6: 	this.y_coord+=2;		//down 
						if (y_coord == Params.world_height) {this.y_coord =0;}
						if (y_coord > Params.world_height) {this.y_coord =1;}
						break;
			case 7:		this.x_coord+=2;		//down right
						this.y_coord+=2;
						if (x_coord == Params.world_width) {this.x_coord =0;}
						if (y_coord == Params.world_height) {this.y_coord =0;}
						if (x_coord > Params.world_width) {this.x_coord =1;}
						if (y_coord > Params.world_height) {this.y_coord =1;}
						break;
			}// switch statement
			}// else statement (not a fight)
		}//moved if statement
		moved = 1;                                      // indicate it has moved
		this.energy -= Params.run_energy_cost;			//use the energy
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy) return;				//not enough energy to reproduce
				
		offspring.energy = this.energy/2;									//set child energy to half parents
				
		this.energy = (int) Math.ceil(this.energy/2.0);						//set parent energy to half and round up
				
		offspring.walk(direction);											//assign adjacent position 
				
		babies.add(offspring);												//add new child to list of babies
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	public static void worldTimeStep() {
		for(Critter crit: population) {		//do time step for each critter
			crit.doTimeStep();
		}
		
		//Implementing encounters here
		ArrayList<Critter> copy = new ArrayList<Critter>();			//holds copy of population so I can modify pop
		for (int i=0; i < population.size();i++)                        // (Critter crit: population)
		{
			Critter crit = population.get(i);
			copy.add(crit);
		}
		ArrayList<ArrayList<Critter>> difEncounters = new ArrayList<ArrayList<Critter>>();	// list of all different encounters	
		
		for(int i =0; i<copy.size();i++) {                             //          (Critter crit: copy)       
			
			Critter crit = copy.get(i);
			ArrayList<Critter> encounter = new ArrayList<Critter>();    // holds encounter of crit with all other critters in array
			for(int j=0; j<copy.size();j++) {                            //while(it.hasNext())
				
				Critter comp = copy.get(j);			//critter to compare to
				if(comp != crit) {                  //make sure not the same critter
					if(crit.x_coord==comp.x_coord && crit.y_coord==comp.y_coord) {
						encounter.add(comp);
						copy.remove(comp);				//remove comp from copy of pop
					}
				}
			}
			if(!encounter.isEmpty()) {
				encounter.add(crit);					//add the current critter
				difEncounters.add(encounter);			//add to list of different encounters
			}
		}
		
		
		for (int i=0; i<difEncounters.size();i++)             // resolve encounters    (ArrayList<Critter> encounters: difEncounters)
		{
			ArrayList<Critter> encounters = difEncounters.get(i);
			for (int j =0; j< encounters.size();j++)            //if dead from other encounters then remove (Critter crit: encounters)
			{
				Critter crit = encounters.get(j);
				if (crit.energy<=0)
				{
					encounters.remove(0);
				}
			}//remove dead encounters for loop
			

			
			for (int j =0; j<encounters.size();j++)                       //(Critter crit: encounters)     
			{
				if (encounters.size()>1) {	
					Critter crit = encounters.get(0);
					Critter opponent = encounters.get(1);
					
					
					if (crit.getX()==opponent.getX() && crit.getY()==opponent.getY())      // both are in same position so fight
					{
						crit.fight = 1;
						boolean fightcrit = crit.fight(opponent.toString());    // A fights B
						crit.fight = 1;
						boolean fightop = opponent.fight(crit.toString());    //B fights A
						if (crit.getX()==opponent.getX() && crit.getY()==opponent.getY()) {
						int cr; 
						int op;
						
						if (fightcrit) {
							if(crit.getEnergy() > 0) 
								cr = Critter.getRandomInt(crit.energy);
							else
								cr = 0;
						}
						else {cr = 0;}
						if (fightop) {
							if(crit.getEnergy() > 0)
								op = Critter.getRandomInt(crit.energy);
							else 
								op = 0;
						}
						else {op = 0;}
								
					
						
						if (cr >= op)                                    //determining winners
						{
							crit.energy += (opponent.energy/2);
							opponent.energy = 0;
							encounters.remove(1);
						}
						else
						{
							opponent.energy += (crit.energy/2);
							crit.energy =0;
							encounters.remove(0);
						}
						}
						crit.fight = 0;
						opponent.fight = 0;
					}// fight if statement
				}//only evaluate if there is something to encounter with
				}//evaluate encounter for loop
			
			
		}//end of encounter resolution
		for (int i=0; i<population.size();i++)                  //remove energy for rest
		{
			population.get(i).restEnergy();
		}
		
		//Implementing reproduction here
		for (int i =0; i < babies.size();i++)                  //add babies after encounters are resolved       (Critter crit: babies)
		{
			population.add(babies.get(i));
			babies.remove(i);
		}
		
		for (int i =0; i< population.size();i++)                //remove dead critters  (Critter crit: population)
		{
			Critter crit = population.get(i);
			if (crit.getEnergy()<=0)
			{
				population.remove(i);
			}
		}

		for (int i=0; i<Params.refresh_algae_count;i++)                       //refresh algae count
		{
			 try
			 	{makeCritter("Algae");}
			 catch (Exception e) {
					System.out.println(new InvalidCritterException("assignment5.Algae"));
				}
		}
		
		for (int i=0; i<population.size();i++)                 //reset moved so it can move in the next timestep
		{
			population.get(i).moveReset();
		}
		for (int i=0; i<population.size();i++)
		{
			population.get(i).fight = 0;
		}
	}
	
	public static void displayWorld(Object pane) {

		if (pane == null)
		{
			population.clear();
			return;
		}
		
		double width = (int)((GridPane)pane).getWidth()/Params.world_width*.99; 
		double height = (int)((GridPane)pane).getHeight()/Params.world_height*.99;
		double xpoint = ((GridPane)pane).getWidth()/Params.world_width * 0.1;
		double xspan = ((GridPane)pane).getWidth()/Params.world_width * .8;
		double ypoint = ((GridPane)pane).getHeight()/Params.world_height * 0.1;
		double yspan = ((GridPane)pane).getHeight()/Params.world_height * .8;
		((GridPane)pane).getChildren().clear();
		Critter crit;
		
		int animationtype = 0;    // 0 for jump, 1 for rotate
		
		for (int i=0; i<population.size();i++)
		{
			final Canvas canvas = new Canvas(width,height);    //    canvas size
//			((GridPane)pane).heightProperty().addListener(new ChangeListener<Object>() {
//
//				@Override
//				public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//					double scale = (double)newValue / (double)oldValue;
//					canvas.resize(canvas.getWidth()*scale, canvas.getHeight()*scale);
//					//width *= scale;
//				}
//				
//			});
			GraphicsContext gc = canvas.getGraphicsContext2D();
			try {
				crit = population.get(i);
				CritterShape critshape = crit.viewShape();
				if (critshape.toString().equals("SQUARE"))
				{
					gc.setFill(crit.viewOutlineColor());
					gc.fillRect(0,0,width,height);
					gc.setFill(crit.viewFillColor());
					gc.fillRect(xpoint,ypoint,xspan,yspan);
					animationtype=0;
					
				}
				
				else if (critshape.toString().equals("CIRCLE"))
				{
					
					gc.setFill(crit.viewOutlineColor());
					gc.fillOval(0, 0, width, height);
					gc.setFill(crit.viewFillColor());
					gc.fillOval(xpoint,ypoint,xspan,yspan);
					animationtype = 0;
				}
				else if (critshape.toString().equals("STAR"))
				{

					double xpoints[] = {width*.03, width*.28, width*.36, width*.45, width*.70, 
							width*.53, width*.56, width*.36, width*.14, width*.17};
				    double ypoints[] = {height*.28, height*.21, height*.027, height*.21, height*.24, height*.35,
				    		height*.53, height*.42, height*.53, height*.35};

				        
				        animationtype = 1;
				        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border x shift
				        {
				        	xpoints[bxsh] = xpoints[bxsh]+(width*.15);
				        	
				        }
//				        
				        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border y shift
				        {
				        	ypoints[bxsh] = ypoints[bxsh]+(height*.2);	
				        }
//				        
//				       
				        gc.strokePolygon(xpoints, ypoints, xpoints.length);
				        gc.setFill(crit.viewOutlineColor());
				        gc.fillPolygon(xpoints, ypoints, xpoints.length);
				        
				        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border y shift
				        {
				        	xpoints[bxsh] = xpoints[bxsh]*.7;
				        	xpoints[bxsh] = xpoints[bxsh]+(width*.16);
				        	
				        	ypoints[bxsh] = ypoints[bxsh]*.7;
				        	ypoints[bxsh] = ypoints[bxsh]+(height*.15);
				        }
				        
				        gc.strokePolygon(xpoints, ypoints, xpoints.length);
				        gc.setFill(crit.viewFillColor());
				        gc.fillPolygon(xpoints, ypoints, xpoints.length);
				        
				}
				else if (critshape.toString().equals("TRIANGLE"))
				{

						double xpoints[] = {width*.5,width,0 };
					    double ypoints[] = {0, height, height};
					    
//					    for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border x shift
//				        {
//				        	xpoints[bxsh] = xpoints[bxsh]+(width*.2);
//				        	
//				        }
				        
//				        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border y shift
//				        {
//				        	ypoints[bxsh] = ypoints[bxsh]+(height*.2);	
//				        }
				       
//					   
					    animationtype = 1;
				        gc.strokePolygon(xpoints, ypoints, xpoints.length);
				        gc.setFill(crit.viewOutlineColor());
				        gc.fillPolygon(xpoints, ypoints, xpoints.length);
				        
				        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border y shift
				        {
				        	xpoints[bxsh] = xpoints[bxsh]*.8;
				        	xpoints[bxsh] = xpoints[bxsh]+(width*.10);
				        	
				        	ypoints[bxsh] = ypoints[bxsh]*.8;
				        	ypoints[bxsh] = ypoints[bxsh]+(height*.12);
				        }
				        
				        gc.strokePolygon(xpoints, ypoints, xpoints.length);
				        gc.setFill(crit.viewFillColor());
				        gc.fillPolygon(xpoints, ypoints, xpoints.length);
				        

				}
				
				else if (critshape.toString().equals("DIAMOND"))
				{
				double xpoints[] = { 0, width*.5, width, width*.5,};
				double ypoints[] = { height*.5, height*.03, height*.5, height};
		       
		  
				animationtype = 1;
		        gc.strokePolygon(xpoints, ypoints, xpoints.length);
		        gc.setFill(crit.viewOutlineColor());
		        gc.fillPolygon(xpoints, ypoints, xpoints.length);
		        
		        for (int bxsh =0;  bxsh < xpoints.length;bxsh++)  //border y shift
		        {
		        	xpoints[bxsh] = xpoints[bxsh]*.8;
		        	xpoints[bxsh] = xpoints[bxsh]+(width*.10);
		        	
		        	ypoints[bxsh] = ypoints[bxsh]*.8;
		        	ypoints[bxsh] = ypoints[bxsh]+(height*.10);
		        }
		        
		        gc.strokePolygon(xpoints, ypoints, xpoints.length);
		        gc.setFill(crit.viewFillColor());
		        gc.fillPolygon(xpoints, ypoints, xpoints.length);
		        
//		        gc.strokePolygon(xpointss, ypointss, xpointss.length);
//		        gc.setFill(crit.viewFillColor());
//		        gc.fillPolygon(xpointss, ypointss, xpointss.length);
				}
				
					TranslateTransition bounce = new TranslateTransition();
					RotateTransition spin = new RotateTransition();
					if (animationtype ==0)
						{bounce.setDuration(Duration.seconds(.5));
						bounce.setNode(canvas);
						bounce.setToY(-50);
						bounce.setAutoReverse(true);
						bounce.setCycleCount(2);}
					else if (animationtype==1)
					{
						spin.setDuration(Duration.seconds(1));
						spin.setNode(canvas);
						spin.setByAngle(360);
						spin.setRate(3);
						spin.setCycleCount(1);
					}
			
				GridPane.setConstraints(canvas, crit.x_coord, crit.y_coord);
				((GridPane)pane).add(canvas, crit.x_coord, crit.y_coord);
				if (animationtype ==0) bounce.play();
				if (animationtype==1) spin.play();
				
			}
			catch (Exception e)
			{
				System.out.println("Error");
			}
		}
		
		
	} 
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		if(critter_class_name.charAt(0) >= 'a') {
			char[] temp = critter_class_name.toCharArray();
			temp[0] = (char) (temp[0] - 32);
			critter_class_name = temp.toString();
			throw new InvalidCritterException(critter_class_name);
		}
		Class<?> critter = null;
		try {
			critter = Class.forName("assignment5."+critter_class_name);		//get class
			Object newCritter = critter.getConstructor().newInstance();			//create object
			((Critter) newCritter).x_coord = Critter.getRandomInt(Params.world_width);		//set energy and location
			((Critter) newCritter).y_coord = Critter.getRandomInt(Params.world_height);
			((Critter) newCritter).energy = Params.start_energy;
			population.add((Critter) newCritter);					//add to population
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> thisClass = null;
		try {
			thisClass = Class.forName("assignment5."+critter_class_name);
			//Object obj = thisClass.getConstructor().newInstance();
			for(Critter crit: population) {
				if(thisClass.isInstance(crit)) {
					result.add(crit);
				}
			}
		} catch (Exception e) {
			throw new InvalidCritterException(critter_class_name);
		}		
		return result;
	}
	
	public static String runStats(List<Critter> critters) {
		String retS = "";		//return string
		retS = "" + critters.size() + " critters as follows -- ";
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			prefix += s + ":" + critter_count.get(s);
			prefix += ", ";
		}
		prefix += "\n";
		retS = retS + prefix;
		return retS;
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}
	
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		Iterator<Critter> it = population.iterator();
		while(it.hasNext()) {
			it.next();		//remove the critter
			it.remove();
		}
	}
		
}
