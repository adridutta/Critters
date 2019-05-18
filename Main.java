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

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import assignment5.Critter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.*;
import java.util.concurrent.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;



public class Main extends Application{
	public static boolean cgstat = false;
	private static boolean enable = false;
	private static Integer animSpeed;
	//static Stage primaryStage = new Stage();
	static GridPane grid = new GridPane();
	static GridPane crittergrid = new GridPane();
	private static AudioClip theme;
	private static ArrayList<String> runstatlist = new ArrayList<String>(); 
	private static TextArea area = new TextArea();      //run stats solution
	
	
	
	public static void main(String[] args) {
		launch(args);
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Critters");
		//grid.setAlignment(Pos.CENTER);           //align grid to center
		//grid.setHgap(10);        //splits 
		//grid.setVgap(10);
		//grid.setPadding(new Insets(25,25,25,25));
		
		//theme.play();
		//theme.setCycleCount(AudioClip.INDEFINITE);
		
		
		theme = new AudioClip(this.getClass().getResource("Undertale.wav").toString());
		theme.setVolume(.3);
		theme.play();
		theme.setCycleCount(AudioClip.INDEFINITE);
		
		crittergrid.setPrefWidth(750);
		crittergrid.setPrefHeight(750);
		GridPane.setConstraints(crittergrid, 0, 0,12,3);     //position of critter grid; colind, rowind, colspan, rowspan
		grid.getChildren().add(crittergrid);
		grid.setStyle("-fx-background-image: url('https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/0kjHIH6/fireflies-flying-on-a-starry-night-background_b0kcdjrig_thumbnail-full01.png')");
			
			for (int i =0; i<=Params.world_width;i++)          // 750 by 750 pixel grid                      
			{
				ColumnConstraints column = new ColumnConstraints();
				column.setPercentWidth(crittergrid.getWidth()/Params.world_width);
				crittergrid.getColumnConstraints().add(column);
			}
			
			for (int i =0; i<=Params.world_height;i++)                                   
			{
				RowConstraints row = new RowConstraints();
				row.setPercentHeight(crittergrid.getHeight()/Params.world_height);
				crittergrid.getRowConstraints().add(row);
			}
					
			Random rand = new Random(System.currentTimeMillis());			//get true random number
			int num = rand.nextInt(4);
			//int num = 4;
			switch(num) {
			case 0: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png.jpg')");
					break;				
			case 1: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/grass_template2.jpg')");
					break;				
			case 2: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png')");				
					break;
			case 3: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/backgroundSpace_01.1.png')");				
					break;
			case 4: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/kachel6f.png.preview.jpg')");
					break;				
			}
			crittergrid.setGridLinesVisible(true);
			
			
			
			
			//https://d2v9y0dukr6mq2.cloudfront.net/video/thumbnail/H8WuRINimqur8ud/sky-and-motion-of-the-clouds-timelapse-timelapse-videos-where-the-sun-shines-through-the-clouds-on-the-blue-sky-background-4k-high-speed-clouds-movement-timelapse_b5dny76__F0005.png
			
		
//		String critter = new String("Craig");
//		String alg = new String("Algae");
		
//		try{
//			for (int i = 0; i < 2; i++)
//			Critter.makeCritter(critter);
//			Critter.makeCritter(alg);
//			}
//		catch (Exception e) {System.out.println("error2");}
//		Critter.displayWorld(crittergrid);
		
			
			
			
		
		
		
		//Following is for user input for time steps
		Spinner<Integer> stepOptions = new Spinner<Integer>(1,1000,1);			//user enters how many steps theyd like to take
		stepOptions.setPrefWidth(100);
		stepOptions.setEditable(true);
		
		
		
		Button stepSel = new Button();                  //button for step
		stepSel.setText("Timesteps");
		stepSel.setPrefWidth(100);
		grid.add(stepOptions, 1, 5); //col then row
		grid.add(stepSel, 1, 6);
		stepSel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				Integer input = stepOptions.getValue();
				
					try {
						
						for (Integer i=0; i<input;i++)
	        			{
	        				Critter.worldTimeStep();
	        			}
					} catch(Exception e1) {
						System.out.println("Error");
					}
				//Critter.displayWorld(grid);
			}
		});
		
		//Display world
		Button display = new Button();                  //display world
		display.setText("Display World!");
		display.setPrefWidth(100);
		grid.add(display, 1, 7); //col then row
		display.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				Critter.displayWorld(crittergrid);
				runstatupdater(area);
			}
		});
		
		//This is to create new critters
		TextField critName = new TextField(); // textfield to enter crit name
		critName.setPrefHeight(50);
		critName.setPrefWidth(100);
		critName.setText("Critter Name: ");
		
		ChoiceBox<String> critterlist = new ChoiceBox<>();
		ArrayList<String> critternames = new ArrayList<String>();
		
		critterlist.getItems().add("Select Critter");     //prompt
		critterlist.setValue("Select Critter");          //set prompt to default
		
		try {                                        //find all critter names
			critternames = findall();
		} catch (ClassNotFoundException e) {
			System.out.println("Error finding critter");
		}
		
		for (String critter: critternames)
		{
			critterlist.getItems().add(critter);
		}
		
		
		Spinner<Integer> makeSpinner = new Spinner<Integer>(1,1000,1);;             //make 1
		makeSpinner.setPrefWidth(100);
		makeSpinner.setEditable(true);
		
		Button make = new Button();             //make 10
		make.setText("Make Critter");
		make.setPrefWidth(100);
		
		
		critterlist.minWidth(100);
		GridPane.setConstraints(critterlist, 2, 5);
		grid.getChildren().add(critterlist);
		grid.add(makeSpinner, 2, 6);
		grid.add(make, 2, 7);
		make.setOnAction(new EventHandler<ActionEvent>() {         //make button press
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				String input = (String)critterlist.getValue();
				String none = new String ("Select Critter");
				Integer amount = makeSpinner.getValue();
				
				if (!input.equals(none))
					  try {
							for (int i=0; i<amount;i++) {Critter.makeCritter(input);}				
					} catch(Exception e1) {
						critName.setText("Invalid Critter!");
					}
				
				
				//Critter.displayWorld(crittergrid);
			}
		});
		
		
		
		
		
		
		//This area to set seed
		Spinner<Integer> seeds = new Spinner<Integer>(1,1000,1); 
		seeds.setPrefWidth(100);
		seeds.setEditable(true);
		grid.add(seeds, 4, 5);
		
		
		Button setSeed = new Button();
		setSeed.setText("Set Seed");
		setSeed.setPrefWidth(100);
		grid.add(setSeed, 4, 6);
		
		
		setSeed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				int input = seeds.getValue();
				Critter.setSeed(input);	
				//area.appendText(Critter.getRandomInt(90)+"");
			}
		});
		
		
		//This area is for quit button
		Button quit = new Button();
		quit.setPrefWidth(100);
		quit.setText("QUIT!");
		grid.add(quit, 4, 7);
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		//This is for runstats
		area.setPrefHeight(1000);               //area for runstat
		area.setPrefWidth(500);
		area.setEditable(false);
		area.setText("Run Stats\n");
		grid.add(area, 13, 0); //column, row
		ChoiceBox<String> runstatOptions = new ChoiceBox<>();     //choicebox of which critter to runstat for
		runstatOptions.getItems().add("Select Critter");
		runstatOptions.setValue("Select Critter");
		runstatOptions.prefWidth(100);
		try {
			ArrayList<String> runchoices = findall();               //put in all the critter choices
			for (int i=0; i<runchoices.size();i++)
			{
				runstatOptions.getItems().add(runchoices.get(i));
			}
		}
		catch (Exception e)
		{
			System.out.println("Error");
		}
		Button getStats = new Button();         //button to run stats
		getStats.setText("Run Stats\n");
		getStats.setPrefWidth(100);
		grid.add(runstatOptions, 0, 5);
		grid.add(getStats, 0, 6);
		getStats.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				String input = (String)runstatOptions.getValue();
				boolean visible = false;
				
				for (String critters: runstatlist)          // compare input to already there
				{
					if (critters.equals(input))
					{
						visible = true;
					}
				}
				
				if(visible == false) {                  //only update if not there
					runstatlist.add(input);
					runstatupdater(area);
				}
			}
		});
		
		ChoiceBox<String> removestatsoptions = new ChoiceBox<String>();       //choice box to remove critter stats
		removestatsoptions.getItems().add("Remove Critter");
		removestatsoptions.setValue("Remove Critter");
		removestatsoptions.setPrefWidth(100);
		
		
		
		
		
		for (String critters:critternames)                          //add all the choices
		{
			removestatsoptions.getItems().add(critters);
		}
		grid.add(removestatsoptions, 0, 7);
		
		Button removeStats = new Button();    //button to remove critter stats
		removeStats.setText("Remove Stats");
		removeStats.setPrefWidth(100);
		grid.add(removeStats, 0, 8);
		removeStats.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				String input = (String)removestatsoptions.getValue();
				boolean visible = false;
				
				for (String critters: runstatlist)          // compare input to already there
				{
					if (critters.equals(input))
					{
						visible = true;
					}
				}
				
				if(visible == true) {                  //only update if not there
					runstatlist.remove(input);
					runstatupdater(area);
				}
			}
		});
		
		//for animation
		Spinner<Integer> animationOptions = new Spinner<Integer>(1,1000,1); 
		animationOptions.setPrefWidth(100);
		animationOptions.setEditable(true);
		grid.add(animationOptions, 3, 5);
		
		
		
		
		Button music = new Button();
		music.setText("Music");
		music.setPrefWidth(100);
		grid.add(music, 3, 8);
		music.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (theme.isPlaying())
				{
					theme.stop();
				}
				else
				{
					theme.play();
					theme.setCycleCount(AudioClip.INDEFINITE);
				}

			}
		});
		
		
		// change parameters
		
		ChoiceBox<String> paramOptions = new ChoiceBox<String>();
		paramOptions.setPrefWidth(200);
		paramOptions.getItems().add(new String("Select Parameter"));
		paramOptions.getItems().add(new String("World width"));
		paramOptions.getItems().add(new String("World Height"));
		paramOptions.getItems().add(new String("Walk energy cost"));
		paramOptions.getItems().add(new String("Run energy cost"));
		paramOptions.getItems().add(new String("Rest energy cost"));
		paramOptions.getItems().add(new String("Reproduction energy"));
		paramOptions.getItems().add(new String("Algae refresh rate"));
		paramOptions.getItems().add(new String("Photosynthesis rate"));
		paramOptions.getItems().add(new String("Start energy"));
		paramOptions.getItems().add(new String("Look energy cost"));
		
		paramOptions.setValue("Select Parameter");
		
		Spinner<Integer> paramsAmount = new Spinner<Integer>(1,1000,1);
		paramsAmount.setPrefWidth(200);
		paramsAmount.setEditable(true);
		Button paramsButton = new Button("Change Parameter");
		paramsButton.setPrefWidth(200);
		
		paramsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (paramOptions.getValue().equals("Select Parameter"))
				{
					return;
				}
				else if (paramOptions.getValue().equals("World width"))
				{
					Params.world_width = paramsAmount.getValue();
					Random rand = new Random(System.currentTimeMillis());			//get true random number
					int num = rand.nextInt(4);
					//int num = 4;
					switch(num) {
					case 0: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png')");
							break;				
					case 1: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/grass_template2.jpg')");
							break;				
					case 2: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png')");				
							break;
					case 3: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/backgroundSpace_01.1.png')");				
							break;
					case 4: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/kachel6f.png.preview.jpg')");
							break;				
					}
					
					Critter.displayWorld(null);
				}
				
				else if (paramOptions.getValue().equals("World Height"))
				{
					Params.world_height = paramsAmount.getValue();
					Random rand = new Random(System.currentTimeMillis());			//get true random number
					int num = rand.nextInt(4);
					//int num = 4;
					switch(num) {
					case 0: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png')");
							break;				
					case 1: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/grass_template2.jpg')");
							break;				
					case 2: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/space_2.png')");				
							break;
					case 3: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/backgroundSpace_01.1.png')");				
							break;
					case 4: crittergrid.setStyle("-fx-background-image: url('https://opengameart.org/sites/default/files/kachel6f.png.preview.jpg')");
							break;				
					}
					
					Critter.displayWorld(null);
				}
				else if (paramOptions.getValue().equals("Walk energy cost"))
				{
					Params.walk_energy_cost = paramsAmount.getValue();
				}
				
				else if (paramOptions.getValue().equals("Run energy cost"))
				{
					Params.run_energy_cost = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Rest energy cost"))
				{
					Params.rest_energy_cost = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Reproduction energy"))
				{
					Params.min_reproduce_energy = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Algae refresh rate"))
				{
					Params.refresh_algae_count = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Photosynthesis rate"))
				{
					Params.photosynthesis_energy_amount = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Start energy"))
				{
					Params.start_energy = paramsAmount.getValue();
				}
				else if (paramOptions.getValue().equals("Look energy cost"))
				{
					Params.look_energy_cost = paramsAmount.getValue();
				}
				

			}
		});
		
		
		grid.add(paramOptions, 5, 5);
		grid.add(paramsAmount, 5, 6);
		grid.add(paramsButton, 5, 7);
		
		Scene scene = new Scene(grid, 1000, 1000);        //make new scene to display
		
		scene.heightProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				area.setPrefHeight((double)newValue/2);
				double scale = (double)newValue / (double)oldValue;
				crittergrid.setPrefHeight(crittergrid.getHeight()*scale);
				Critter.displayWorld(crittergrid);
				
			}
			
		});
		
		scene.widthProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				area.setPrefWidth((double)newValue/2);
				double scale = (double)newValue / (double)oldValue;
				crittergrid.setPrefWidth(crittergrid.getWidth()*scale);
				Critter.displayWorld(crittergrid);
				
			}
			
		});
		
		
		primaryStage.setScene(scene);                  // now scene displays
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.show();                        // display to user
		grid.setGridLinesVisible(false);
		
		Button animationButton = new Button();
		animationButton.setText("Animate!");
		animationButton.setPrefWidth(100);
		grid.add(animationButton, 3, 6);
		animationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				animSpeed = (Integer)animationOptions.getValue();
				enable = true;
				
				stepSel.setDisable(true);                    //disable all buttons
				display.setDisable(true);
				make.setDisable(true);
				getStats.setDisable(true);
				removeStats.setDisable(true);
				animationButton.setDisable(true);
				setSeed.setDisable(true);
				quit.setDisable(true);
				paramsButton.setDisable(true);
	
				Timer animationtimer = new Timer();
				animationtimer.scheduleAtFixedRate(new animation(), 100,1500);
				
//			
			}
		});
		
		
		
		//Stop animation
		Button stopAnimation = new Button();
		stopAnimation.setText("Stop Animation");
		grid.add(stopAnimation, 3, 7);
		stopAnimation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AudioClip note = new AudioClip(this.getClass().getResource("Laugh1.wav").toString());
				note.play();
				enable = false;
				stepSel.setDisable(false);                    //enable buttons
				display.setDisable(false);
				make.setDisable(false);
				getStats.setDisable(false);
				removeStats.setDisable(false);
				animationButton.setDisable(false);
				setSeed.setDisable(false);
				quit.setDisable(false);
				paramsButton.setDisable(false);
				

			}
		});
		
	}
	
	
	
	private ArrayList<String> findall() throws ClassNotFoundException   
	{
		try {
		ArrayList<String> critters = new ArrayList<String>();
		URL source = Thread.currentThread().getContextClassLoader().getResource("assignment5");
		
		File[] allfiles = new File(source.getPath()).listFiles();
		
		for (File file: allfiles)
		{
			if (file.getName().endsWith(".class"))
			{
				String fileName = file.getName();
				int extension = fileName.indexOf(".");
				String allclass = fileName.substring(0, extension);
				if(Critter.class.isAssignableFrom(Class.forName("assignment5." + allclass)) && !allclass.equals("Critter")&& !allclass.equals("Critter$TestCritter"))
				{
					critters.add(allclass);
				}
				
			}
		}
		
		
		return critters;
		}
		catch (Exception e)
		{
			throw new ClassNotFoundException();
		}
		
		
	}
	
	private static void graph()
	{
		
		for (String critters:runstatlist)
		{
			try {
				List<Critter> list = Critter.getInstances(critters);
 
    		} catch(Exception e1) {
    			System.out.println("Runstat error");
    		}
	
		
		HBox root = new HBox();
		
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("TimeStep");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Critter Amount");
		
		LineChart<Number, Number> critter = new LineChart<Number,Number>(xAxis,yAxis);
		critter.setTitle(critters);
		
		XYChart.Series<String, Number> data = new XYChart.Series<>();
		
		}
	}
	
	private static void runstatupdater(TextArea runstatbox)
	{
		runstatbox.clear();
		runstatbox.appendText("Run Stats\n");
		for (String critters:runstatlist)
		{
			try {
				List<Critter> list = Critter.getInstances(critters);
        		if (list.size()==0)
        		{
        			Class<?> thisclass = Class.forName("assignment5."+critters);
        			Method runstat = thisclass.getMethod("runStats", List.class);
        			String stats = (String) runstat.invoke(list, list);
        			runstatbox.appendText(stats+"\n");
   
        		}
        		else {
	        		Class<?> critterclass = list.get(0).getClass();
	        		Method runstats = critterclass.getMethod("runStats", List.class);
	        		String stats = (String) runstats.invoke(list.get(0).getClass(), list);
	        		runstatbox.appendText(stats+"\n");
        		}
        		graph();
        		
        	
    		} catch(Exception e1) {
    			System.out.println("Runstat error");
    		}
			
		}
	}
	class animation extends TimerTask{
			
			public void run() {
				Platform.runLater(()->{
				if (enable) {
					for (Integer i=0; i<animSpeed;i++)     // speed based on input
						{Critter.worldTimeStep();}
					Critter.displayWorld(crittergrid);
					Main.runstatupdater(area);
				}
					
				});

			}
	}
	
	
	
}














