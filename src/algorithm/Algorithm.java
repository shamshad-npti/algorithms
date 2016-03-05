package algorithm;

import ds.tree.RedBlackTree;
import dsp.Complex;
import dsp.FFT;
import java.util.Arrays;
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
import matrix.Matrix;

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
        double[] d = new double[100000];
        for(int i = 0; i < d.length; i++) {
            d[i] = (int)(100 * Math.random());
        }
        Complex[] complex = FFT.FFT(d, 1 << 20);
        //System.out.println(Arrays.toString(complex));
        Complex[] inv = FFT.IFFT(complex, 1 << 20);
        //System.out.println(Arrays.toString(inv));
        for(int i = 0; i < d.length; i++) {
            if(!equals(d[i], inv[i].len(), 1e-8)) {
                System.out.println("Error" + i);
            }
        }
        System.exit(0);
    }
    
    public static boolean equals(double d1, double d2, double eps) {
        return (d1 - eps) < d2 && d2 < (d1 + eps);
    }
}
