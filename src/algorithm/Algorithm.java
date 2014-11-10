/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import cg.LineDr;
import cg.LineDrB;
import cg.LineDrDDA;
import cg.ShapeObj;
import ds.BinaryTree;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author shamshad
 */
public class Algorithm extends Application {
    private void execute() {
        System.out.print("WW");
    }
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((e) -> {
            //System.out.println("Hello World");
            execute();
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame();
        final ShapeObj l = new LineDrB(new Point(10, 10), new Point(100, 500));
        f.getContentPane().add(new JPanel(){

            @Override
            public void paint(Graphics g) {
                l.draw(g);
            }
            
        }, BorderLayout.CENTER);
        f.setSize(500, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
}
