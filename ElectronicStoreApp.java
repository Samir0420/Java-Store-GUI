import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ElectronicStoreApp extends Application {
    private ElectronicStore electronicStore;
    private double totalCost;


    public void start(Stage primaryStage) {
        // Create ElectronicStore instance
        electronicStore = ElectronicStore.createStore();

        Pane aPane = new Pane();

        AppView view = new AppView();

        view.update(electronicStore, 1);

        aPane.getChildren().add(view);

        primaryStage.setTitle("Electronic Store Application"); //Set the title for the window
        primaryStage.setResizable(false); //Make it non-resizable
        primaryStage.setScene(new Scene(aPane, 800,400)); //Set the dimensions of the window
        primaryStage.show(); //Display the window

        //EVENT HANDLING

        //When Add Button is pressed
        view.getAddToCartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedItem = view.getStoreStockList().getSelectionModel().getSelectedItem();
                electronicStore.updateStock(selectedItem); //Call updateStock method for selectedItem

                if (selectedItem != null) {
                    // Check if the item has already been added to the target list view
                    int count = 1;
                    int index = -1;
                    for (int i = 0; i < view.getCurrentCartList().getItems().size(); i++) {
                        String item = view.getCurrentCartList().getItems().get(i);
                        if (item.contains(selectedItem)) {
                            int startIndex = item.indexOf("x");
                            if (startIndex != -1) {
                                int endIndex = item.indexOf(" ", startIndex);
                                count = Integer.parseInt(item.substring(startIndex + 1, endIndex).trim()) + 1;
                                index = i;
                                break;
                            }
                        }
                    }

                    // Add the item to the target list view with the count
                    String itemWithCount = "x" + count + " " + selectedItem;
                    if (index == -1) {
                        view.getCurrentCartList().getItems().add(itemWithCount);
                    } else {
                        view.getCurrentCartList().getItems().remove(index);
                        view.getCurrentCartList().getItems().add(index, itemWithCount);
                    }
                    for(Product p : electronicStore.getStock()){
                        if (p != null){
                            if (p.toString().equals(selectedItem)){
                                totalCost += p.getPrice();
                            }
                        }
                    }
                    view.setLabel7("Current Cart($"+totalCost+"):");
                    view.update(electronicStore, 0); //Update view
                }
            }
        });

        //When remove from cart button is pressed
        view.getRemoveFromCartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedItem = view.getCurrentCartList().getSelectionModel().getSelectedItem();
                electronicStore.removeItem(selectedItem);

                view.getCurrentCartList().getItems().remove(selectedItem);

                for(Product p : electronicStore.getStock()){
                    if (p != null){
                        if (p.toString().equals(selectedItem)){
                            totalCost -= p.getPrice();
                        }
                    }
                }
                view.setLabel7("Current Cart($"+totalCost+"):");
                view.update(electronicStore, 0); //Update view
            }
        });



    }

}
