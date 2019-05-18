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
 * Slip days used: <1>
 * Fall 2018
 * https://github.com/EE422C-Fall-2018/project-5-critters-2-project-5-pair-28
 */
import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;

/* CRITTERS Critter.java
 * EE422C Project 4 submission by
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
public class Critter4 extends Critter {
	
	@Override
	public String toString() {
		return "4";
	}
	
	@Override
	public void doTimeStep()
	{
		if (this.getEnergy()>= Params.min_reproduce_energy)
		{
			Critter baby = new Critter4();
			this.reproduce(baby, 1);
		}
	}
	
	public boolean fight(String opp) { 
		if (this.getEnergy()>=Params.run_energy_cost)
		{
			this.run(0);
			return false;
		}
		
		else if (this.getEnergy()>=Params.walk_energy_cost)
		{
			this.walk(1);
			return false;
		}
		else return true;
		
	}
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return Color.BLACK; }
	public javafx.scene.paint.Color viewFillColor() { return Color.LIGHTBLUE; }
	
	public  CritterShape viewShape()
	{
		return CritterShape.SQUARE;
	}

}
