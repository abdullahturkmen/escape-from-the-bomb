module escape-from-the-bomb {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
}
