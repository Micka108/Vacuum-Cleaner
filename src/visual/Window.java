package visual;

import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
//import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Window extends Application {
    
    private AnchorPane ap_scene;
    private BorderPane bp_all;
    private static GridPane gp_floor;
    private static GridPane gp_robot;
    private static GridPane gp_score;
    private StackPane sp_plateau;
    private StackPane sp_score;
    
    private Rectangle rt_score;
    
    private Label lb_score;
    private Label lb_dirt;
    private Label lb_jewel;
    private Label lb_battery;
    private Label lb_all;
    private Label lb_sucked;
    private Label lb_picked;
    
    private static Text tt_jewela;
    private static Text tt_jewels;
    private static Text tt_jewelp;
    private static Text tt_dirta;
    private static Text tt_dirts;
    private static Text tt_battery;
    private static Text tt_score;
    
    private static ListView<String> lv_travel;
    private static ObservableList<String> items;

    private static ImageView robot;
    
    private static int incView = 1;
    private static RadioButton rb_explore;
    private static RadioButton rb_learning;

    private static final CountDownLatch latch = new CountDownLatch(1);
    
    
    // Init interface
    public Window(){
         this.create_widgets();
         this.modify_widgets();
         this.create_layouts();
         this.modify_layouts();
         this.add_widgets_to_layouts();
         this.add_layouts_to_layouts();      
    }
    
    // Create widgets
    private void create_widgets() {
        // Rectangle
        this.rt_score = new Rectangle(450.00, 200.00);
        // Label
        this.lb_score = new Label("Score");
        this.lb_jewel = new Label("Jewel");
        this.lb_dirt = new Label("Dirt");
        this.lb_battery = new Label("Battery");
        this.lb_sucked = new Label("Sucked");
        this.lb_picked = new Label("Picked");
        this.lb_all = new Label("All");
        // ListView
        Window.lv_travel = new ListView<String>();
        // Text
        Window.tt_jewela = new Text("0");
        Window.tt_jewels = new Text("0");
        Window.tt_jewelp = new Text("0");
        Window.tt_dirta = new Text("0");
        Window.tt_dirts = new Text("0");
        Window.tt_battery = new Text("0");
        Window.tt_score = new Text("0");
        
        Window.rb_explore = new RadioButton("BFS / A* Search");
        Window.rb_learning = new RadioButton("Apprentissage (Oui/Non)");
    }
    
    // Modify widgets
    private void modify_widgets() {
        
        // Modify Rectangle
        this.rt_score.setArcHeight(40.00);
        this.rt_score.setArcWidth(40.00);
        
        // Modify Label
        this.lb_dirt.setGraphic(new ImageView(new Image(Path.Dirt, 36, 36, false, false)));
        this.lb_jewel.setGraphic(new ImageView(new Image(Path.Jewel, 36, 36, false, false)));
        this.lb_battery.setGraphic(new ImageView(new Image(Path.Battery, 36, 36, false, false)));
        
        // Modify ListView
        Window.items = FXCollections.observableArrayList();
        Window.lv_travel.setItems(items);
        Window.lv_travel.setOrientation(Orientation.VERTICAL);
        Window.lv_travel.setPrefSize(450, 200);
    }
    
    // Create layouts
    private void create_layouts() {
        this.ap_scene = new AnchorPane();
        this.bp_all = new BorderPane();
        Window.gp_floor = new GridPane();
        Window.gp_robot = new GridPane();
        Window.gp_score = new GridPane();
        this.sp_score = new StackPane();
        this.sp_plateau = new StackPane();
    }
    
    // Modify layouts
    private void modify_layouts() {
        // Modify GridPane
        Window.gp_floor.setAlignment(Pos.CENTER);
        Window.gp_robot.setAlignment(Pos.CENTER);
        Window.gp_score.setAlignment(Pos.CENTER);
        Window.gp_score.setGridLinesVisible(false);
        Window.gp_score.setId("gp_score");
        Window.gp_score.setHgap(10);
        Window.gp_score.setVgap(5);
        Window.gp_score.setPadding(new Insets(20));

        // Modify BorderPane
        this.bp_all.setLayoutX(10);
        this.bp_all.setLayoutY(10);
        
        // Modify AnchorPane
        BackgroundImage BI = new BackgroundImage(new Image(Path.Manor, 780,780, false, false), 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        
        //this.ap_scene.setStyle("-fx-background-color: #FFFFFF;");
        this.ap_scene.setBackground(new Background(BI));
        
        BorderPane.setAlignment(this.sp_plateau, Pos.CENTER);
        BorderPane.setAlignment(Window.lv_travel, Pos.CENTER);
        BorderPane.setMargin(this.sp_score, (new Insets(10, 10, 10, 10)));
        BorderPane.setMargin(this.sp_plateau, (new Insets(10, 10, 10, 10)));
        BorderPane.setMargin(Window.lv_travel, (new Insets(10, 10, 10, 10)));
    }   
    
    // Add widgets to layouts
    private void add_widgets_to_layouts() {
        
        // Init Floor
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                ImageView pic = new ImageView(new Image(Path.Parquet, 64, 64, false, false));
                Window.gp_floor.add(pic, x,y);
            }
        }
        
        // Init Robot
        for(int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(64);
            RowConstraints row = new RowConstraints(64);
            column.setHalignment(HPos.CENTER);
            row.setValignment(VPos.CENTER);
            Window.gp_robot.getColumnConstraints().add(column);
            Window.gp_robot.getRowConstraints().add(row);
        }
        
        // Init Score
        int[] dim = {90,50,80,80,80};
        for(int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(dim[i]);
            column.setHalignment(HPos.CENTER);
            Window.gp_score.getColumnConstraints().add(column);
        }

        // Score
        Window.gp_score.add(this.lb_battery, 0, 0);
        Window.gp_score.add(Window.tt_battery, 0, 1);
        Window.gp_score.add(this.lb_all, 1, 1);
        Window.gp_score.add(this.lb_sucked, 1, 2);
        Window.gp_score.add(this.lb_picked, 1, 3);
        Window.gp_score.add(this.lb_dirt, 2, 0);
        Window.gp_score.add(Window.tt_dirta, 2, 1);
        Window.gp_score.add(Window.tt_dirts, 2, 2);
        Window.gp_score.add(this.lb_jewel, 3, 0);
        Window.gp_score.add(Window.tt_jewela, 3, 1);
        Window.gp_score.add(Window.tt_jewels, 3, 2);
        Window.gp_score.add(Window.tt_jewelp, 3, 3);
        Window.gp_score.add(this.lb_score, 4, 0);
        Window.gp_score.add(Window.tt_score, 4, 1);
        Window.gp_score.add(Window.rb_explore, 0, 4, 2, 1);
        Window.gp_score.add(Window.rb_learning, 2, 4, 4, 1);
        
        // Pane Principal
        this.bp_all.setTop(this.sp_score);
        this.bp_all.setCenter(this.sp_plateau);
        this.bp_all.setBottom(Window.lv_travel);
    }
    
    // Add layouts to layouts
    private void add_layouts_to_layouts() {
        // Add to principal pane
        this.ap_scene.getChildren().add(this.bp_all);
        // Add to StackPane
        this.sp_score.getChildren().addAll(this.rt_score, Window.gp_score);
        this.sp_plateau.getChildren().addAll(Window.gp_floor, Window.gp_robot);
    }
  
    public static void awaitFXToolkit() throws InterruptedException {
        latch.await();
     }
    
    @Override
    public void init() {
        latch.countDown();
    }
    
    // Add dirt in floor
    public static void addDirt(int x, int y) {
        ImageView pic = new ImageView(new Image(Path.Dirt, 50, 50, false, false));
        pic.setId("dirt");
        gp_floor.add(pic, x, y);
        GridPane.setHalignment(pic, HPos.CENTER);
    }
    
    // Add jewel in floor
    public static void addJewel(int x, int y) {
        ImageView pic = new ImageView(new Image(Path.Jewel, 50, 50, false, false));
        pic.setId("jewel");
        Window.gp_floor.add(pic, x, y);
        GridPane.setHalignment(pic, HPos.CENTER);
    }
    
    // Remove dirt in floor
    public static void removeDirt(int x, int y) {
        for (Node node : gp_floor.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node.getId() == "dirt") {
                //System.out.println(node.getId());
            	Window.gp_floor.getChildren().remove(node);
                break;
            }
        }
    }
    
    // Remove jewel in floor
    public static void removeJewel(int x, int y) {
        for (Node node : Window.gp_floor.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node.getId() == "jewel") {
                System.out.println(node.getId());
                Window.gp_floor.getChildren().remove(node);
                break;
            }
        }
    }
    
    // Init robot in floor
    public static void initRobot(int x, int y) {
        robot = new ImageView(new Image(Path.VacuumCleaner, 50, 50, false, false));
        robot.setId("robot");
        Window.gp_robot.add(robot, x, y);
        GridPane.setHalignment(robot, HPos.CENTER);
    }
    
    // Get position robot
    public static int[] getPosRobot() {
        int x = GridPane.getColumnIndex(robot);
        int y = GridPane.getRowIndex(robot);
        int[] pos = {x, y};
        return pos;
    }
    
    public static boolean getExplore() {
    	boolean bool = Window.rb_explore.isSelected();
		return bool;
    }
    
    public static boolean getLearning() {
    	boolean bool = Window.rb_learning.isSelected();
		return bool;
    }
    
    // Move Robot
    public static void moveRobot(Direction dir) {
        int[] pos = getPosRobot();
        switch(dir) {
            case Top:
                GridPane.setConstraints(Window.robot, pos[0], pos[1]-1);
                break;
            case Left:
                GridPane.setConstraints(Window.robot, pos[0]-1, pos[1]);
                break;
            case Down:
                GridPane.setConstraints(Window.robot, pos[0], pos[1]+1);
                break;
            case Right:
                GridPane.setConstraints(Window.robot, pos[0]+1, pos[1]);
                break;
        }
    }

    // Set score {Battery, Dirt All, Dirt Sucked, Jewel All, Jewel Sucked, Jewel Picked, Score}
    public static void setScore(int[] score) {
        Text[] widText = {Window.tt_battery,Window.tt_dirta,Window.tt_dirts,Window.tt_jewela,Window.tt_jewels,Window.tt_jewelp,Window.tt_score};
        for(int i = 0; i < widText.length; i++) {
            String s = Integer.toString(score[i]);
            widText[i].setText(s);
        }
    }
    
    // Print in ListView
    public static void printDirection(String dir) {
        items.add(incView + ": " + dir);
        incView++;
    }

    public void p() {
        for (Node node : Window.gp_floor.getChildren()) {
            System.out.println(node);
            System.out.println(GridPane.getColumnIndex(node));
            System.out.println(GridPane.getRowIndex(node));
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    	//Stage stage = new Stage();
        Scene scene = new Scene(this.ap_scene,500,800);
        scene.getStylesheets().add(getClass().getResource(Path.CSS).toExternalForm());
        //scene.
        stage.getIcons().add(new Image(Path.VacuumCleaner));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Vacuum-Cleaner");
        stage.show();
    }
    
    /*public static void main(String[] args) {
        launch(args);
    }*/
}
