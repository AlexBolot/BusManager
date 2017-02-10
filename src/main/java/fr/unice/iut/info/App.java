package fr.unice.iut.info;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*.........................................................
 . Copyright (c)                                          .
 .                                                        .
 . Code créé par :                                        .
 . -> Alexandre BOLOT                                     .
 . -> Victor MONSCH                                       .
 . -> Christopher SABOYA                                  .
 . -> Laurent MICHELET                                    .
 .                                                        .
 . Dernière modification le : 10/02/17 14:03              .
 .........................................................*/

public class App extends Application
{
    public static void main (String[] args) throws IOException, InterruptedException
    {
        //region Scanner
        /*Scanner sc = new Scanner(System.in);
        
        List<String> listCLI = Arrays.asList("CLI", "Cli", "cli");
        List<String> listGUI = Arrays.asList("GUI", "Gui", "gui");
        List<String> options = Arrays.asList("exit", "q", "stop", "CLI", "Cli", "cli", "GUI", "Gui", "gui");
        
        System.out.println("Quel Intercace utiliser ?" + "\n  -> Command Line Interface (CLI)" + "\n  -> Graphical User Interface (GLI)" + "\n  -> Quitter (q)");
        
        String answer = sc.nextLine();
        
        while (!options.contains(answer))
        {
            System.out.println("Saisie Incorrecte");
            answer = sc.nextLine();
        }
        
        if(listCLI.contains(answer))
        {
            new CLIView().initialize();
        }
        else if(listGUI.contains(answer))
        {
            launch(args);
        }
        else
        {
            System.exit(0);
        }*/
        //endregion
        
        new CLIView().start();
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI.fxml"));
            primaryStage.setTitle("Home");
            primaryStage.setScene(new Scene(root, 566, 650));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }
    }
}
