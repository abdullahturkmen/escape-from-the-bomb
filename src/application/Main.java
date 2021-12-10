package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FlowPane pane = new FlowPane();
			/*String x="Welcome to Escape from the Bomb";
			Label []enter =new Label[31];
			
			for(int i=0;i<31;i++) {
				enter[i].setText(String.valueOf(x.charAt(i)));
				pane.add(e, i, i, i, i);
			}
			*/
			Label label=new Label();
			label.setText("       Welcome to Escape from the Bomb        ");
			label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
			label.setTextFill(Color.WHITE);
			Label label2=new Label();
			label2.setFont(Font.font("Times New Roman", FontPosture.ITALIC,10));
			label2.setText("                                                 (Please select a game board)        ");
			label2.setTextFill(Color.WHITE);
			
			
			//pane.getChildren().add(label);
			//label.setAlignment(Pos.TOP_CENTER);
			
		
			//enter.setPrefSize(20, 20);
			Button ten=new Button("10x10");
			Button fifteen=new Button("15x15");
			Button twenty=new Button("20x20");
			
			
			
		
			//enter.setPadding(new Insets(10,10,10,10));
			
			//pane.addRow(5, enter);
			//pane.getChildren().add(enter);
			
			
			ten.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
			fifteen.setFont(Font.font("Helvetica  ", FontWeight.BOLD, 15));
			twenty.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
		
			/*ten.setStyle("-fx-background-color: lightgreen");
			fifteen.setStyle("-fx-background-color: yellow");
			twenty.setStyle("-fx-background-color: red");*/
			
			
			/*ten.setStyle("-fx-border-color: black");
			fifteen.setStyle("-fx-border-color: black");
			twenty.setStyle("-fx-border-color: black");
			*/
			pane.getChildren().addAll(label,label2,ten,fifteen,twenty);
			pane.setPadding(new Insets(30, 30, 30, 30));
			pane.setVgap(60);
			pane.setHgap(81); //yanlamasýna aralýk
			
			
			/*pane.setTop(label);
			pane.setCenter(ten);
			pane.setCenter(fifteen);
			pane.setCenter(twenty);
			
			ten.setAlignment(Pos.CENTER_LEFT);
			fifteen.setAlignment(Pos.CENTER);
			twenty.setAlignment(Pos.CENTER_RIGHT);
			*/
			
			
			/*pane.setVgap(10);
			pane.setHgap(10);
			//pane.add(enter, 3, 1);
			pane.add(ten, 5, 20);
			pane.add(fifteen, 10, 20);
			pane.add(twenty,15,20);*/
			Image image=new Image("https://mpng.subpng.com/20180306/ljw/kisspng-bomb-weapon-cartoon-bomb-vector-hand-painted-5a9e7906dc2fd7.4838742115203351109019.jpg");

			pane.setStyle("-fx-background-image :url(https://thumbs.dreamstime.com/b/minefield-mines-explosives-bombs-background-illustration-full-fuse-lit-each-bomb-going-to-go-boom-87753649.jpg)" );
			
			
			//pane.getChildren().addAll(ten,fifteen,twenty);
			
			//Image image=new Image("https://mpng.subpng.com/20180306/ljw/kisspng-bomb-weapon-cartoon-bomb-vector-hand-painted-5a9e7906dc2fd7.4838742115203351109019.jpg");
			ImageView imageview=new ImageView(image);
			
			
			Scene scene = new Scene(pane,430,400);
			
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(image);
			primaryStage.setTitle("Escape From the Bomb");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
