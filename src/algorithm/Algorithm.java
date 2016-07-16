package algorithm;

import ds.tree.RedBlackTree;
import dsp.Complex;
import dsp.FFT;
import java.util.Arrays;
import java.util.List;
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
import str.UkkonenAlgo;

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
    private static String randomString(int length) {
        int a = 'a';
        Random r = new Random();
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < length; i++) {
            char c = (char) (a + r.nextInt(26));
            b.append(c);
        }
        b.append('`');
        return b.toString();
    }
    
    public static void main(String[] args) throws Exception {
        int size = 100000;
        String s = "aaaa`";//"aabbaabbccaabbccd";//randomString(size);
        List<Integer> suffix = UkkonenAlgo.toSuffixArray(s);
        System.out.println(suffix);
        System.out.println("Verifying Solution");
        for(int i = 1; i < s.length(); i++) {
            String s1 = s.substring(suffix.get(i - 1));
            String s2 = s.substring(suffix.get(i));
            System.out.println(s1);
            if(s1.compareTo(s2) > 0) {
                System.out.println("Error");
                System.out.println(s1);
                System.out.println(s2);
                break;
            }
        }
        System.exit(0);
    }
    
    public static boolean equals(double d1, double d2, double eps) {
        return (d1 - eps) < d2 && d2 < (d1 + eps);
    }
}
