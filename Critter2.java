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

import java.util.List;

import assignment5.Critter.CritterShape;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

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
public class Critter2 extends Critter{
	@Override
	public String toString() {
		return "2";
	}
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter2() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String other) { 
		if(other.equals("Craig")) return false;
		return Critter.getRandomInt(GENE_TOTAL) <= 12;
	}

	@Override
	public void doTimeStep() {
			
		if (getEnergy() > 200) {
			Critter2 child = new Critter2();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.genes[g] -= 1;
			g = Critter.getRandomInt(8);
			child.genes[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		int randomNum = this.getRandomInt(8);
		String safe = look(randomNum, true);
		if(safe != null)
			this.run(randomNum);				//always runs cause its impatient
	}

	public static String runStats(List<Critter> crit2) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : crit2) {
			Critter2 c = (Critter2) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		String retS = "";
		retS += "" + crit2.size() + " total Critter2    \n";
		retS += "" + total_straight / (GENE_TOTAL * 0.01 * crit2.size()) + "% straight   \n";
		retS += "" + total_back / (GENE_TOTAL * 0.01 * crit2.size()) + "% back   \n";
		retS += "" + total_right / (GENE_TOTAL * 0.01 * crit2.size()) + "% right   \n";
		retS += "" + total_left / (GENE_TOTAL * 0.01 * crit2.size()) + "% left   \n";
		return retS;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return Color.BLACK; }
	public javafx.scene.paint.Color viewFillColor() { return Color.BURLYWOOD; }
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.TRIANGLE;
	}
}
