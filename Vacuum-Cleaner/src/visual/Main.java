package visual;
    

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

public class Main extends Application {
    
    private AnchorPane ap_scene;
    private BorderPane bp_all;
    private GridPane gp_floor;
    private GridPane gp_robot;
    private GridPane gp_score;
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
    
    private Text tt_jewela;
    private Text tt_jewels;
    private Text tt_jewelp;
    private Text tt_dirta;
    private Text tt_dirts;
    private Text tt_battery;
    private Text tt_score;
    
    private ListView<String> lv_travel;
    private ObservableList<String> items;

    private ImageView robot;
    
    private int incView = 1;
    private ChoiceBox<String> cb_type;
    private ChoiceBox<String> cb_app;

    // Init interface
    public void init(){
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
        this.lv_travel = new ListView<String>();
        // Text
        this.tt_jewela = new Text("0");
        this.tt_jewels = new Text("0");
        this.tt_jewelp = new Text("0");
        this.tt_dirta = new Text("0");
        this.tt_dirts = new Text("0");
        this.tt_battery = new Text("0");
        this.tt_score = new Text("0");
        
        this.cb_type = new ChoiceBox<String>(FXCollections.observableArrayList("Formelle", "Informelle"));
        this.cb_app = new ChoiceBox<String>(FXCollections.observableArrayList("Apprentissage", "Sans"));
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
        this.items = FXCollections.observableArrayList();
        this.lv_travel.setItems(items);
        this.lv_travel.setOrientation(Orientation.VERTICAL);
        this.lv_travel.setPrefSize(450, 200);
    }
    
    // Create layouts
    private void create_layouts() {
        this.ap_scene = new AnchorPane();
        this.bp_all = new BorderPane();
        this.gp_floor = new GridPane();
        this.gp_robot = new GridPane();
        this.gp_score = new GridPane();
        this.sp_score = new StackPane();
        this.sp_plateau = new StackPane();
    }
    
    // Modify layouts
    private void modify_layouts() {
        // Modify GridPane
        this.gp_floor.setAlignment(Pos.CENTER);
        this.gp_robot.setAlignment(Pos.CENTER);
        this.gp_score.setAlignment(Pos.CENTER);
        this.gp_score.setGridLinesVisible(false);
        this.gp_score.setId("gp_score");
        this.gp_score.setHgap(10);
        this.gp_score.setVgap(5);
        this.gp_score.setPadding(new Insets(20));

        // Modify BorderPane
        this.bp_all.setLayoutX(20);
        this.bp_all.setLayoutY(10);
        
        // Modify AnchorPane
        BackgroundImage BI = new BackgroundImage(new Image(Path.Manor, 1080,728, false, false), 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        
        //this.ap_scene.setStyle("-fx-background-color: #FFFFFF;");
        this.ap_scene.setBackground(new Background(BI));
        
        BorderPane.setAlignment(this.sp_plateau, Pos.CENTER);
        BorderPane.setAlignment(this.lv_travel, Pos.CENTER);
        BorderPane.setMargin(this.sp_score, (new Insets(10, 10, 10, 10)));
        BorderPane.setMargin(this.sp_plateau, (new Insets(10, 10, 10, 10)));
        BorderPane.setMargin(this.lv_travel, (new Insets(10, 10, 10, 10)));
    }   
    
    // Add widgets to layouts
    private void add_widgets_to_layouts() {
        
        // Init Floor
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                ImageView pic = new ImageView(new Image(Path.Parquet, 64, 64, false, false));
                this.gp_floor.add(pic, x,y);
            }
        }
        
        // Init Robot
        for(int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(64);
            RowConstraints row = new RowConstraints(64);
            column.setHalignment(HPos.CENTER);
            row.setValignment(VPos.CENTER);
            this.gp_robot.getColumnConstraints().add(column);
            this.gp_robot.getRowConstraints().add(row);
        }
        
        // Init Score
        int[] dim = {90,50,80,80,80};
        for(int i = 0; i < 5; i++) {
            ColumnConstraints column = new ColumnConstraints(dim[i]);
            column.setHalignment(HPos.CENTER);
            this.gp_score.getColumnConstraints().add(column);
        }

        // Score
        this.gp_score.add(this.lb_battery, 0, 0);
        this.gp_score.add(this.tt_battery, 0, 1);
        this.gp_score.add(this.lb_all, 1, 1);
        this.gp_score.add(this.lb_sucked, 1, 2);
        this.gp_score.add(this.lb_picked, 1, 3);
        this.gp_score.add(this.lb_dirt, 2, 0);
        this.gp_score.add(this.tt_dirta, 2, 1);
        this.gp_score.add(this.tt_dirts, 2, 2);
        this.gp_score.add(this.lb_jewel, 3, 0);
        this.gp_score.add(this.tt_jewela, 3, 1);
        this.gp_score.add(this.tt_jewels, 3, 2);
        this.gp_score.add(this.tt_jewelp, 3, 3);
        this.gp_score.add(this.lb_score, 4, 0);
        this.gp_score.add(this.tt_score, 4, 1);
        //this.gp_score.add(this.cb_type, 1, 4, 2,1);
        //this.gp_score.add(this.cb_app, 3, 4, 3,1);
        
        // Pane Principal
        this.bp_all.setTop(this.sp_score);
        this.bp_all.setCenter(this.sp_plateau);
        this.bp_all.setBottom(this.lv_travel);
    }
    
    // Add layouts to layouts
    private void add_layouts_to_layouts() {
        // Add to principal pane
        this.ap_scene.getChildren().add(this.bp_all);
        // Add to StackPane
        this.sp_score.getChildren().addAll(this.rt_score, this.gp_score);
        this.sp_plateau.getChildren().addAll(this.gp_floor, this.gp_robot);
    }
    
    // Add floor
    public void addFloor(int x, int y) {
        ImageView pic = new ImageView(new Image(Path.Parquet, 64, 64, false, false));
        this.gp_floor.add(pic, x, y);
    }
    
    // Add dirt in floor
    public void addDirt(int x, int y) {
        ImageView pic = new ImageView(new Image(Path.Dirt, 50, 50, false, false));
        pic.setId("dirt");
        this.gp_floor.add(pic, x, y);
        GridPane.setHalignment(pic, HPos.CENTER);
    }
    
    // Add jewel in floor
    public void addJewel(int x, int y) {
        ImageView pic = new ImageView(new Image(Path.Jewel, 50, 50, false, false));
        pic.setId("jewel");
        this.gp_floor.add(pic, x, y);
        GridPane.setHalignment(pic, HPos.CENTER);
    }
    
    // Remove dirt in floor
    public void removeDirt(int x, int y) {
        for (Node node : this.gp_floor.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node.getId() == "dirt") {
                //System.out.println(node.getId());
                this.gp_floor.getChildren().remove(node);
                break;
            }
        }
    }
    
    // Remove jewel in floor
    public void removeJewel(int x, int y) {
        for (Node node : this.gp_floor.getChildren()) {
            if (node instanceof ImageView && GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node.getId() == "jewel") {
                System.out.println(node.getId());
                this.gp_floor.getChildren().remove(node);
                break;
            }
        }
    }
    
    // Init robot in floor
    public void initRobot(int x, int y) {
        this.robot = new ImageView(new Image(Path.VacuumCleaner, 50, 50, false, false));
        this.robot.setId("robot");
        this.gp_robot.add(this.robot, x, y);
        GridPane.setHalignment(this.robot, HPos.CENTER);
    }
    
    // Get position robot
    public int[] getPosRobot() {
        int x = GridPane.getColumnIndex(robot);
        int y = GridPane.getRowIndex(robot);
        int[] pos = {x, y};
        return pos;
    }
    
    // Move Robot
    public void moveRobot(Direction dir) throws InterruptedException {
        int[] pos = this.getPosRobot();
        switch(dir) {
            case Top:
                GridPane.setConstraints(this.robot, pos[0], pos[1]-1);
                break;
            case Left:
                GridPane.setConstraints(this.robot, pos[0]-1, pos[1]);
                break;
            case Down:
                GridPane.setConstraints(this.robot, pos[0], pos[1]+1);
                break;
            case Right:
                GridPane.setConstraints(this.robot, pos[0]+1, pos[1]);
                break;
        }
    }

    // Set score {Battery, Dirt All, Dirt Sucked, Jewel All, Jewel Sucked, Jewel Picked, Score}
    public void setScore(int[] score) {
        Text[] widText = {this.tt_battery,this.tt_dirta,this.tt_dirts,this.tt_jewela,this.tt_jewels,this.tt_jewelp,this.tt_score};
        for(int i = 0; i < widText.length; i++) {
            String s = Integer.toString(score[i]);
            widText[i].setText(s);
        }
    }
    
    // Print in ListView
    public void printDirection(String dir) {
        this.items.add(this.incView + ": " + dir);
        this.incView++;
    }

    public void p() {
        for (Node node : this.gp_floor.getChildren()) {
            System.out.println(node);
            System.out.println(GridPane.getColumnIndex(node));
            System.out.println(GridPane.getRowIndex(node));
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.ap_scene,500,800);
        scene.getStylesheets().add(getClass().getResource(Path.CSS).toExternalForm());
        //scene.
        stage.getIcons().add(new Image(Path.VacuumCleaner));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Vacuum-Cleaner");
        stage.show();
        
        // Test
        this.printDirection("sqdqsdsqd");
        this.printDirection("sqdqsdsqd");
        int[] p = {1,25,2,3,4,5,3};
        this.setScore(p);
        this.initRobot(2, 2);
        //this.addJewel(1, 2);
        this.addDirt(1, 2);
        //this.removeJewel(1, 2);
        //this.p();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
