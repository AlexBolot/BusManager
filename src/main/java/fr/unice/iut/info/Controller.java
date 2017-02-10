package fr.unice.iut.info;

import java.util.ArrayList;
import java.util.Collection;
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
 Classe du Controller qui fait le lien entre
 Les Interfaces et le BusManager.
 
 Elle sert en quelque sorte de Messager entre les classes.
 
 De cette façon, on peut changer l'interface
 ou le BusManager sans problème.
 
 === Nécessite la Classe BusManager ===
 */
public class Controller
{
    private BusManager manager = new BusManager();
    
    /**
     Initialise la relation Observer/Observable
     entre l'interface et le model.
     === Nécessite la Classe BusManager ===
     
     @param observer Observer à ajouter au model
     */
    public void initialize (Observer observer)
    {
        Object o = SerializationClass.read();
        
        manager = (o != null && o instanceof BusManager) ? (BusManager) o : new BusManager();
        
        manager.addObserver(observer);
        manager.start();
    }
    
    /**
     Ordonne au BusManager de s'ajouter un Bus.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à créer (0)
     @return True si tout s'est bien passé
     */
    public boolean createBus (ArrayList<String> args)
    {
        if(args.size() != 1) return false;
        
        return manager.createBus(args.get(0));
    }
    
    /**
     Ordonne au BusManager d'ajouter une Box
     dans le Bus demandé.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à chercher (0)
     ———————————————————— le nom de la Box à créer (1)
     @return True si tout s'est bien passé
     */
    public boolean createBox (ArrayList<String> args)
    {
        if(args.size() != 2) return false;
        
        return manager.createBox(args.get(0), args.get(1));
    }
    
    /**
     Ordonne au BusManager de poster un Message
     dans la Box demandée du Bus demandé.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à chercher       (0)
     ———————————————————— le nom de la Box à chercher    (1)    [Facultatif]
     ———————————————————— le contenu du Message à poster (2)    devient (1) si Box non saisie
     @return True si tout s'est bien passé
     */
    public boolean emitMessage (ArrayList<String> args)
    {
        if(args.size() < 2) return false;
        if(args.size() > 3) return false;
        
        switch (args.size())
        {
            case 2:
                return manager.emit(args.get(0), args.get(1));
            
            case 3:
                return manager.emit(args.get(0), args.get(1), args.get(2));
            
            default:
                return false;
        }
    }
    
    /**
     Ordonne au BusManager de lire le(s) Message(s)
     dans la Box demandée du Bus demandé.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à lire       (0)    [Facultatif]
     ———————————————————— le nom de la Box à lire    (1)    [Facultatif]
     @return True si tout s'est bien passé
     */
    public Collection<String> readMessage (ArrayList<String> args)
    {
        if(args.size() > 2) return new ArrayList<String>();
        
        switch (args.size())
        {
            case 0:
                return manager.getAllMessages();
            
            case 1:
                return manager.getAllMessages(args.get(0));
            
            case 2:
                return manager.getAllMessages(args.get(0), args.get(1));
            
            default:
                return new ArrayList<String>();
        }
    }
    
    /**
     Demande au BusManager s'il possède déjà le Bus demandé.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à chercher (0)
     @return True si le bus est déjà dans le BusManager
     */
    public boolean hasExistingBus (ArrayList<String> args)
    {
        if(args.size() != 1) return false;
        return manager.hasExistingBus(args.get(0));
    }
    
    /**
     Demande au BusManager s'il possède déjà la Box
     demandée dans le Bus demandé.
     === Nécessite la Classe BusManager ===
     
     @param args contient le nom du Bus à chercher      (0)
     ———————————————————— le nom de la Box à chercher   (1)
     @return True si la Box du Bus est déjà dans le BusManager
     */
    public boolean hasExistingBox (ArrayList<String> args)
    {
        if(args.size() != 2) return false;
        return manager.hasExistingBox(args.get(0), args.get(1));
    }
    
    /**
     Retourne l'ensemble des noms des Bus du BusManager
     === Nécessite la Classe BusManager ===
     
     @return la liste des noms des Bus
     */
    public Collection<String> getAllBusNames ()
    {
        return manager.getAllBusNames();
    }
    
    /**
     Retourne l'ensemble des noms des Box d'un Bus [busName]
     === Nécessite la Classe BusManager ===
     
     @param busName nom du Bus à chercher
     @return la liste des noms des Box
     */
    public Collection<String> getAllBoxNames (String busName)
    {
        return manager.getAllBoxNames(busName);
    }
}
