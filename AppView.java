import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class AppView extends Pane {
    private ListView<String> popItemList, storeStockList, currentCartList;
    private TextField numSales, revenueText, saleText;
    private Button resetStoreButton, addToCartButton, removeFromCartButton, completeSaleButton;
    private double totalPrice = 0.0;
    private Label label7;



    public AppView(){
        //Create the labels (MAKE SURE TO RELOCATE CORRECTLY)
        Label label1 = new Label("Store Summary:");
        label1.relocate(30,20);

        Label label2 = new Label("# Sales:");
        label2.relocate(15,45);

        Label label3 = new Label("Revenue:");
        label3.relocate(15,85);

        Label label4 = new Label("$/Sale:");
        label4.relocate(15,125);

        Label label5 = new Label("Most Popular Items:");
        label5.relocate(25,165);

        Label label6 = new Label("Store Stock:");
        label6.relocate(270,50);

        label7 = new Label("Current Cart:");
        label7.relocate(580,50);

        //Create the text field
        numSales = new TextField( "0");
        numSales.relocate(70, 40);
        numSales.setPrefSize(80, 20);

        revenueText = new TextField( "0.00");
        revenueText.relocate(70, 80);
        revenueText.setPrefSize(80, 20);

        saleText = new TextField( "N/A");
        saleText.relocate(70, 120);
        saleText.setPrefSize(80, 20);

        //Make the list view
        popItemList = new ListView<String>();
        popItemList.setItems(FXCollections.observableArrayList());
        popItemList.relocate(10,190); //Relocate the list view
        popItemList.setPrefSize(150,150); //Set the dimensions of the lists

        storeStockList = new ListView<String>();
        storeStockList.setItems(FXCollections.observableArrayList());
        storeStockList.relocate(170,70); //Relocate the list view
        storeStockList.setPrefSize(270,270); //Set the dimensions of the lists

        currentCartList = new ListView<String>();
        currentCartList.setItems(FXCollections.observableArrayList());
        currentCartList.relocate(480,70); //Relocate the list view
        currentCartList.setPrefSize(270,270); //Set the dimensions of the lists

        //Create the buttons
        resetStoreButton = new Button("Reset Store");
        resetStoreButton.setStyle("-fx-font: 12 arial"); //Add font style
        resetStoreButton.relocate(35,350); //Relocate
        resetStoreButton.setPrefSize(95, 40); //The dimensions of the button

        addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-font: 12 arial"); //Add font style
        addToCartButton.relocate(250,350); //Relocate to
        addToCartButton.setPrefSize(95, 40); //The dimensions of the button

        removeFromCartButton = new Button("Remove from Cart");
        removeFromCartButton.setStyle("-fx-font: 12 arial");
        removeFromCartButton.relocate(520,350); //Relocate
        removeFromCartButton.setPrefSize(95, 40); //The dimensions of the button

        completeSaleButton = new Button("Complete Sale");
        completeSaleButton.setStyle("-fx-font: 12 arial");
        completeSaleButton.relocate(620,350); //Relocate
        completeSaleButton.setPrefSize(95, 40); //The dimensions of the button

        // Add all the components to the window
        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, popItemList, storeStockList, currentCartList, resetStoreButton, addToCartButton, removeFromCartButton, completeSaleButton, numSales, saleText, revenueText);

    }
    public void update (ElectronicStore store, int selectedItem){
        Product[] stock = store.getStock();

        ArrayList<String> products = new ArrayList<String>();
        ArrayList<String> mostPopular = new ArrayList<String>();

        for (Product p: stock){
            if(p != null && p.getStockQuantity() > 0){
                products.add(p.toString());
            }
        }

        for (int i =0; i < 3; i++){
            mostPopular.add(store.mostPopularItems().get(i).toString());
        }



        //Input the information into the list
        storeStockList.setItems(FXCollections.observableArrayList(products));
        popItemList.setItems(FXCollections.observableArrayList(mostPopular));

        addToCartButton.disableProperty().bind(storeStockList.getSelectionModel().selectedItemProperty().isNull());
        completeSaleButton.disableProperty().bind(currentCartList.getSelectionModel().selectedItemProperty().isNull());
        removeFromCartButton.disableProperty().bind(currentCartList.getSelectionModel().selectedItemProperty().isNull());
    }
    public ListView<String> getStoreStockList() {return storeStockList;}
    public ListView<String> getCurrentCartList() {return currentCartList;}
    public Button getAddToCartButton() {return addToCartButton;}
    public Button getRemoveFromCartButton() {return removeFromCartButton;}
    public void setLabel7(String totalP){label7.setText(totalP);}
}
