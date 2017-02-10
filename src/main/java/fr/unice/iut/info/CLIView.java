package fr.unice.iut.info;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

/**
 Classe de Code Behind de CLI
 
 Elle permet de controller l'interface
 en ligne de commande.
 
 -> Elle effectue quelques vérifications simples
 (si la réponse saisie est vide par exemple),
 -> Elle gère les changements de menus de l'interface
 -> Elle transmet les instructions au Controller
 
 === Nécessite la Classe Controller ===
 === Nécessite la Classe CLI ===
 */
public class CLIView extends Thread implements Observer
{
    private CLI        cli        = new CLI();
    private Controller controller = new Controller();
    
    /**
     Gère le menu d'accueil de la Commande Ligne Interface.
     Permet de rediriger l'utilisateur vers les différents
     menus suivants ou de quitter l'application.
     
     -> Create Bus
     -> Create Box
     -> Emit Message
     -> Lire Message
     -> Stop
     */
    private void begin ()
    {
        //region get Instructions
        String command = cli.getInstructions();
        
        while (command.isEmpty())
        {
            cli.print("commande invalide");
            command = cli.getInstructions();
        }
        //endregion
        
        switch (command.charAt(0))
        {
            //region createBus 'c'
            case 'c':
                createBus();
                begin();
                break;
            //endregion
            
            //region creatBox 'b'
            case 'b':
                createBox();
                begin();
                break;
            //endregion
            
            //region emitMessage 'e'
            case 'e':
                emitMessage();
                begin();
                break;
            //endregion
            
            //region readMessages 'l'
            case 'l':
                readMessages();
                begin();
                break;
            //endregion
            
            //region stop 's'
            case 's':
                break;
            //endregion
            
            //region default
            default:
                cli.print("Commande incorrecte.");
                begin();
                break;
            //endregion
        }
    }
    
    /**
     Gère le menu de création d'un Bus.
     Récupère la saisie utilisateur et la transmet
     à la Classe Controller
     */
    private void createBus ()
    {
        //region local variables
        String busName;
        Boolean reussi;
        ArrayList<String> args = new ArrayList<String>();
        //endregion
        
        //region get BusName
        cli.printListBus(controller.getAllBusNames());
        busName = cli.getLine("");
        
        while (busName.isEmpty())
        {
            cli.print("Vous devez saisir un nom valide");
            cli.printListBus(controller.getAllBusNames());
            busName = cli.getLine("");
        }
        //endregion
        
        args.add(busName);
        
        reussi = controller.createBus(args);
        
        //region feedBack
        if(reussi)
        {
            cli.print("Bus " + busName + " créé");
        }
        else
        {
            cli.print("Erreur le bus existe déjà");
        }
        //endregion
    }
    
    /**
     Gère le menu de création d'une Box.
     Récupère la saisie utilisateur et la transmet
     à la Classe Controller
     */
    private void createBox ()
    {
        //region local variables
        String busName;
        String boxName;
        Boolean reussi;
        ArrayList<String> args = new ArrayList<String>();
        //endregion
        
        //region get BusName
        cli.printListBus(controller.getAllBusNames());
        busName = cli.getLine("");
        
        while (busName.isEmpty())
        {
            cli.print("Vous devez saisir un nom valide");
            cli.printListBus(controller.getAllBusNames());
            busName = cli.getLine("");
        }
        //endregion
        
        args.add(busName);
        
        if(controller.getAllBoxNames(args.get(0)) != null)
        {
            //region get BoxName
            cli.printListBox(controller.getAllBoxNames(busName));
            boxName = cli.getLine("");
            
            while (boxName.isEmpty())
            {
                cli.print("Vous devez saisir un nom valide");
                cli.printListBox(controller.getAllBoxNames(busName));
                boxName = cli.getLine("");
            }
            //endregion
            
            args.add(boxName);
            
            reussi = controller.createBox(args);
            
            if(reussi)
            {
                cli.print("Boite " + boxName + " créée");
            }
            else
            {
                cli.print("Erreur la boite existe déjà sur ce bus");
            }
        }
        else
        {
            cli.print("Erreur ce bus n'existe pas");
        }
    }
    
    /**
     Gère le menu de publication d'un Message.
     Récupère la saisie utilisateur et la transmet
     à la Classe Controller
     */
    private void emitMessage ()
    {
        //region local variables
        String busName;
        String boxName;
        String messageContent;
        String command;
        Boolean reussi;
        ArrayList<String> args = new ArrayList<String>();
        //endregion
        
        //region get Instructions
        command = cli.getEmitInstructions();
        
        while (command.isEmpty())
        {
            cli.print("commande invalide");
            command = cli.getEmitInstructions();
        }
        //endregion
        
        switch (command.charAt(0))
        {
            //region emit in Bus
            case 'c':
                //region get BusName
                cli.printListBus(controller.getAllBusNames());
                busName = cli.getLine("");
                
                while (busName.isEmpty())
                {
                    cli.print("Vous devez saisir un nom valide");
                    cli.printListBus(controller.getAllBusNames());
                    busName = cli.getLine("");
                }
                //endregion
                
                args.add(busName);
                
                if(controller.hasExistingBus(args))
                {
                    //region get MessageContent
                    messageContent = cli.getLine("Veuillez saisir votre message :");
                    
                    while (messageContent.isEmpty())
                    {
                        cli.print("Vous devez saisir un message valide");
                        messageContent = cli.getLine("");
                    }
                    //endregion
                    
                    args.add(messageContent);
                    
                    reussi = controller.emitMessage(args);
                    
                    if(reussi)
                    {
                        cli.print("Votre message a été posté dans la boîte default du bus " + busName);
                        break;
                    }
                    cli.print("Erreur : Ce message existe déjà dans la boite default du bus " + busName);
                    break;
                }
                cli.print("Erreur : Ce bus n'existe pas.");
                break;
            //endregion
            
            //region emit in Box
            case 'b':
                //region get BusName
                cli.printListBus(controller.getAllBusNames());
                busName = cli.getLine("");
                
                while (busName.isEmpty())
                {
                    cli.print("Vous devez saisir un nom valide");
                    cli.printListBus(controller.getAllBusNames());
                    busName = cli.getLine("");
                }
                //endregion
                
                args.add(busName);
                
                if(controller.hasExistingBus(args))
                {
                    //region get BoxName
                    cli.printListBox(controller.getAllBoxNames(busName));
                    boxName = cli.getLine("");
                    
                    while (boxName.isEmpty())
                    {
                        cli.print("Vous devez saisir un nom valide");
                        cli.printListBox(controller.getAllBoxNames(args.get(0)));
                        busName = cli.getLine("");
                    }
                    //endregion
                    
                    args.add(boxName);
                    
                    if(controller.getAllBoxNames(args.get(0)).contains(args.get(1)))
                    {
                        //region get MessageContent
                        messageContent = cli.getLine("Veuillez saisir votre message :");
                        
                        while (messageContent.isEmpty())
                        {
                            cli.print("Vous devez saisir un message valide");
                            messageContent = cli.getLine("");
                        }
                        //endregion
                        
                        args.add(messageContent);
                        
                        reussi = controller.emitMessage(args);
                        
                        if(reussi)
                        {
                            cli.print("Votre message a été posté dans la boîte " + boxName + " du bus " + busName);
                            break;
                        }
                        cli.print("Erreur : Ce message existe déjà dans cette boîte");
                        break;
                    }
                    cli.print("Erreur : Cette boîte n'existe pas sur ce bus.");
                    break;
                }
                cli.print("Erreur : Ce bus n'existe pas.");
                break;
            //endregion
            
            //region default
            default:
                cli.print("Commande incorrecte.");
                begin();
                break;
            //endregion
        }
    }
    
    /**
     Gère le menu de lecture d'un Message.
     Récupère la saisie utilisateur et la transmet
     à la Classe Controller
     */
    private void readMessages ()
    {
        //region local variables
        String busName;
        String boxName;
        String command = cli.getReadInstructions();
        ArrayList<String> args = new ArrayList<String>();
        //endregion
        
        switch (command.charAt(0))
        {
            //region read in Bus
            case 'c':
                //region get BusName
                cli.printListBus(controller.getAllBusNames());
                busName = cli.getLine("");
                
                while (busName.isEmpty())
                {
                    cli.print("Vous devez saisir un nom valide");
                    cli.printListBus(controller.getAllBusNames());
                    busName = cli.getLine("");
                }
                //endregion
                
                args.add(busName);
                
                if(controller.hasExistingBus(args))
                {
                    cli.printMessages(controller.readMessage(args));
                    break;
                }
                cli.print("Erreur : Ce bus n'existe pas.");
                break;
            //endregion
            
            //region read in Box
            case 'b':
                //region get BusName
                cli.printListBus(controller.getAllBusNames());
                busName = cli.getLine("");
                
                while (busName.isEmpty())
                {
                    cli.print("Vous devez saisir un nom valide");
                    cli.printListBus(controller.getAllBusNames());
                    busName = cli.getLine("");
                }
                //endregion
                
                args.add(busName);
                
                if(controller.hasExistingBus(args))
                {
                    //region get BoxName
                    cli.printListBox(controller.getAllBoxNames(args.get(0)));
                    boxName = cli.getLine("");
                    
                    while (boxName.isEmpty())
                    {
                        cli.print("Vous devez saisir un nom valide");
                        cli.printListBox(controller.getAllBoxNames(args.get(0)));
                        boxName = cli.getLine("");
                    }
                    //endregion

                    args.add(boxName);

                    if(controller.getAllBoxNames(busName).contains(boxName))
                    {
                        cli.printMessages(controller.readMessage(args));
                        break;
                    }
                    cli.print("Erreur : Cette boîte n'existe pas sur ce bus.");
                    break;
                }
                cli.print("Erreur : Ce bus n'existe pas.");
                break;
            //endregion
            
            //region read all
            case 'a':
                cli.printMessages(controller.readMessage(args));
                begin();
                break;
            //endregion
            
            //region default
            default:
                cli.print("Commande incorrecte.");
                begin();
                break;
            //endregion
        }
    }
    
    /**
     Permet à l'interface de se mettre à jour en fonction du Model
     === Nécessite la Classe BusManager ===
     
     @param observable l'objet BusManager instancié par le Controller
     @param object     paramètre non utilisé
     */
    public void update (Observable observable, Object object)
    {
        // Non nécessaire étant donné qu'il n'y a pas d'interface à
        // raffraichir en temps réel.
        // Les données du BusManager sont demandées au Controller
        // au fur et à mesure des actions CLI.
    }
    
    /**
     Permet d'avoir la Command Line Interface active en
     parrallèle de la GUI, sur un différent Thread.
     */
    @Override
    public void run ()
    {
        controller.initialize(this);
        begin();
    }
}