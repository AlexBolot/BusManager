package fr.unice.iut.info.controller;

import fr.unice.iut.info.model.BusManager;
import fr.unice.iut.info.service.Const;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/*................................................................................................................................
 . Copyright (c)
 .
 . The GUIView	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 18/04/17 17:49
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 Classe de Code Behind de GUI.fxml
 
 Elle sert à recevoir les interactions
 de l'utilisateur avec l'interface graphique
 (click, saisie de texte, etc)
 
 -> Elle effectue quelques vérifications simples
 (si le champ de saisie est vide par exemple),
 -> Elle gère les changements visuels sur l'interface
 -> Elle transmet les instructions au Controller
 
 === Nécessite la Classe Controller ===
 === Nécessite la Classe BusManager === (pour la méthode Update)...
 */
public class GUIView implements Observer
{
    //region //=========== FXML Controls ===========//
    public TreeView<String> treeView;
    public ListView<String> listView;
    public TextField        txtBusName;
    public TextField        txtBoxName;
    public TextField        txtMessageContent;
    public Button           btnLireMessages;
    public Button           btnCreerBus;
    public Button           btnEmettreMessage;
    public Button           btnCreerBoite;
    public Button           btnValider;
    public Label            lblFeedBack;
    public Label            lblBusName;
    public Label            lblBoxName;
    public Label            lblMessageContent;
    
    //endregion
    
    private Controller controller = new Controller();
    private Timeline   timeline   = new Timeline();
    
    private int selectedAction = 0;
    
    /**
     Initialise le Controller et masque
     certains controls de l'interface.
     === Nécessite la Classe Controller ===
     */
    @FXML
    public void initialize ()
    {
        controller.initialize(this);
        hideControls();
    }
    
    /**
     Sélectionne le mode "Créer un Bus"
     -> Affiche les controls nécessaire
     
     @param event paramètre non utilisé
     */
    @FXML
    void btnCreerBus_onClick (ActionEvent event)
    {
        hideControls();
        
        selectedAction = 1;
        
        txtBusName.setVisible(true);
        lblBusName.setVisible(true);
        
        btnValider.setVisible(true);
    }
    
    /**
     Sélectionne le mode "Créer une Box"
     -> Affiche les controls nécessaire
     
     @param event paramètre non utilisé
     */
    @FXML
    void btnCreerBoite_onClick (ActionEvent event)
    {
        hideControls();
        
        selectedAction = 2;
        
        txtBusName.setVisible(true);
        lblBusName.setVisible(true);
        
        txtBoxName.setVisible(true);
        lblBoxName.setVisible(true);
        
        btnValider.setVisible(true);
    }
    
    /**
     Sélectionne le mode "Lire un Message"
     -> Affiche les controls nécessaire
     
     @param event paramètre non utilisé
     */
    @FXML
    void btnLireMessages_onClick (ActionEvent event)
    {
        hideControls();
        
        selectedAction = 3;
        
        txtBusName.setVisible(true);
        lblBusName.setVisible(true);
        
        txtBoxName.setVisible(true);
        lblBoxName.setVisible(true);
        
        listView.setVisible(true);
        btnValider.setVisible(true);
    }
    
    /**
     Sélectionne le mode "Emettre un Message"
     -> Affiche les controls nécessaire
     
     @param event paramètre non utilisé
     */
    @FXML
    void btnEmettreMessage_onClick (ActionEvent event)
    {
        hideControls();
        
        selectedAction = 4;
        
        txtBusName.setVisible(true);
        lblBusName.setVisible(true);
        
        txtBoxName.setVisible(true);
        lblBoxName.setVisible(true);
        
        txtMessageContent.setVisible(true);
        lblMessageContent.setVisible(true);
        
        btnValider.setVisible(true);
    }
    
    /**
     Effectue l'action correspondante au mode sélectionné
     -> Lis les champs de saisie
     -> Transmet les instructions au Controller
     -> Affiche le FeedBack en fonctionn de la
     réponse du Controller
     === Nécessite la Classe Controller ===
     
     @param event paramètre non utilisé
     */
    @FXML
    void btnValider_onClick (ActionEvent event)
    {
        String busName;
        String boxName;
        String messageContent;
        Boolean reussi;
        ArrayList<String> args = new ArrayList<String>();
        
        switch (selectedAction)
        {
            //region Créer Bus
            case 1:
                busName = txtBusName.getText();
                
                if(busName.isEmpty())
                {
                    showFeedBack("Vous devez saisir un nom valide", Const.Red);
                }
                else
                {
                    args.add(busName);
                    
                    reussi = controller.createBus(args);
                    
                    if(reussi)
                    {
                        showFeedBack("Bus " + busName + " créé", Const.Green);
                    }
                    else
                    {
                        showFeedBack("Erreur le bus " + busName + " existe déjà", Const.Red);
                    }
                }
                break;
            //endregion
            
            //region Créer Boite
            case 2:
                busName = txtBusName.getText();
                boxName = txtBoxName.getText();
                
                if(busName.isEmpty() || boxName.isEmpty())
                {
                    showFeedBack("Vous devez saisir un nom valide", Const.Red);
                }
                else
                {
                    args.add(busName);
                    if(controller.hasExistingBus(args))
                    {
                        args.add(boxName);
                        
                        reussi = controller.createBox(args);
                        
                        if(reussi)
                        {
                            showFeedBack("Boite " + boxName + " créé dans " + busName, Const.Green);
                        }
                        else
                        {
                            showFeedBack("Erreur la boite " + boxName + " existe déjà dans " + busName, Const.Red);
                        }
                    }
                    else
                    {
                        showFeedBack("Bus " + busName + " n'existe pas", Const.Red);
                    }
                }
                break;
            //rendregion
            
            //region Read Message
            case 3:
                busName = txtBusName.getText();
                boxName = txtBoxName.getText();
                
                //region busName is empty
                if(busName.isEmpty())
                {
                    showFeedBack("Vous devez saisir un nom valide", Const.Red);
                    break;
                }
                //endregion
                
                //region busName == All
                if(busName.equalsIgnoreCase("All"))
                {
                    populateListView(controller.readMessage(args));
                    break;
                }
                //endregion
                
                args.add(busName);
                
                //region busName != All
                if(controller.hasExistingBus(args))
                {
                    //region boxName is empty
                    if(boxName.isEmpty())
                    {
                        showFeedBack("Vous devez saisir un nom valide", Const.Red);
                        break;
                    }
                    //endregion
                    
                    //region boxName == All
                    if(boxName.equalsIgnoreCase("All"))
                    {
                        populateListView(controller.readMessage(args));
                        break;
                    }
                    //endregion
                    
                    args.add(boxName);
                    
                    //region boxName != All
                    if(controller.hasExistingBox(args))
                    {
                        populateListView(controller.readMessage(args));
                        break;
                    }
                    //endregion
    
                    showFeedBack("Boite " + boxName + " n'existe pas sur le bus " + busName, Const.Red);
                    break;
                }
                //endregion
    
                showFeedBack("Bus " + busName + " n'existe pas", Const.Red);
                break;
            //endregion
            
            //region Emit Message
            case 4:
                busName = txtBusName.getText();
                boxName = txtBoxName.getText();
                messageContent = txtMessageContent.getText();
                
                //region messageContent is empty
                if(messageContent.isEmpty())
                {
                    showFeedBack("Vous devez saisir un Message", Const.Red);
                    break;
                }
                //endregion
                
                //region busName is empty
                if(busName.isEmpty())
                {
                    showFeedBack("Vous devez saisir un nom de Bus", Const.Red);
                    break;
                }
                //endregion
                
                args.add(busName);
                
                //region busName !is empty
                if(controller.hasExistingBus(args))
                {
                    if(!boxName.isEmpty()) args.add(boxName);
                    
                    args.add(messageContent);
                    
                    reussi = controller.emitMessage(args);
                    
                    //region message created
                    if(reussi)
                    {
                        
                        if(boxName.isEmpty())
                        {
                            showFeedBack("Message posté dans default de " + busName, Const.Green);
                            break;
                        }
                        showFeedBack("Message posté dans " + boxName + " de " + busName, Const.Green);
                        break;
                        
                    }
                    //endregion
    
                    showFeedBack("Erreur la boite " + boxName + " n'éxiste pas dans " + busName, Const.Red);
                    break;
                }
                //endregion
    
                showFeedBack("Bus " + busName + " n'existe pas", Const.Red);
                break;
            //endregion
            
            //region Default
            default:
                break;
            //endregion
        }
    }
    
    /**
     Permet de vider et masquer les champs de
     saisie entre deux Sélections de l'utilisateur
     */
    private void hideControls ()
    {
        txtBoxName.setText("");
        txtBusName.setVisible(false);
        lblBusName.setVisible(false);
        
        txtBusName.setText("");
        txtBoxName.setVisible(false);
        lblBoxName.setVisible(false);
        
        txtMessageContent.setText("");
        txtMessageContent.setVisible(false);
        lblMessageContent.setVisible(false);
        
        listView.setVisible(false);
        btnValider.setVisible(false);
    }
    
    /**
     Affiche un message [message] de couleur [color]
     dans une zone dédiée et pendant une durée de 4sec
     pour tenir l'utilisateur informé de la réussite ou
     de l'échec de l'action qu'il a effectué
     
     @param message message à afficher à l'utilisateur
     @param color   code couleur HTML à appliquer au message
     */
    private void showFeedBack (String message, String color)
    {
        lblFeedBack.setText(message);
        lblFeedBack.setTextFill(Color.web(color));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), new KeyValue(lblFeedBack.textProperty(), "")));
        timeline.play();
    }
    
    /**
     Permet à l'interface de se mettre à jour en fonction du Model
     === Nécessite la Classe BusManager ===
     
     @param observable l'objet BusManager instancié par le Controller
     @param object     paramètre non utilisé
     */
    public void update (Observable observable, Object object)
    {
        final Observable obs = observable;
        
        if(obs instanceof BusManager)
        {
            Platform.runLater(new Runnable()
            {
                public void run ()
                {
                    BusManager controller = (BusManager) obs;
                    TreeItem<String> BusManager = new TreeItem<String>("BusManager");
                    BusManager.setExpanded(true);
                    
                    for (String BusName : controller.getAllBusNames())
                    {
                        TreeItem<String> Bus = new TreeItem<String>(BusName);
                        Bus.setExpanded(true);
                        
                        for (String boxName : controller.getAllBoxNames(BusName))
                        {
                            TreeItem<String> Box = new TreeItem<String>(boxName);
                            Box.setExpanded(true);
                            
                            Bus.getChildren().add(Box);
                        }
                        BusManager.getChildren().add(Bus);
                    }
                    
                    treeView.setRoot(BusManager);
                    treeView.setShowRoot(false);
                    treeView.refresh();
                }
            });
        }
    }
    
    /**
     Permet de réactualiser la ListView lors d'un "Lire un Message"
     
     @param messages liste des messages à ajouter à la liste view
     */
    private void populateListView (Collection<String> messages)
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        
        for (String s : messages)
        {
            items.add(s);
        }
        
        listView.setItems(items);
    }
}