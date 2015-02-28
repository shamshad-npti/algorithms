package algorithm;

import ds.tree.RedBlackTree;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shamshad
 */
public class Algorithm extends Application {
    private RedBlackTree<Integer, Integer> tr = new RedBlackTree<>();
    private static Random ran = new Random();
    @Override
    public void start(Stage primaryStage) {
        VBox box = new VBox(4);
        HBox hbox = new HBox(4);
        final TextField inp = new TextField("20");
        final ImageView view = new ImageView();
        Button add = new Button("Add");
        Button remove = new Button("Remove");

        final StackPane root = new StackPane();
        root.setPrefHeight(700);
        add.setOnAction((e) -> {
            try {
                tr.insert(Integer.valueOf(inp.getText().trim()), 0);
                inp.setText(" " + ran.nextInt(300));
                root.getChildren().clear();
                root.getChildren().add(tr.draw());
            } catch (Exception ex) {
                System.out.println("Exp");
            }
        });

        remove.setOnAction((e) -> {
            try {
                tr.remove(Integer.valueOf(inp.getText().trim()));
                inp.clear();
                root.getChildren().clear();
                root.getChildren().add(tr.draw());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        box.getChildren().addAll(hbox, root);
        hbox.getChildren().addAll(inp, add, remove);
        Scene scene = new Scene(box, 1296, 700);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
        System.exit(0);
    }
}
