package fr.unice.iut.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;

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
 Classe qui définit le BusManager.
 Un BusManager contient des Bus.
 Un Bus ne peux pas contenir deux Bus identiques.
 
 === Nécessite la Classe Bus ===
 */
public class BusManager extends Observable implements Serializable
{
    final private HashMap<String, Bus> busHashMap;
    
    /**
     Constructeur du Bus.
     Notifie ses Observers.
     === Nécessite la Classe Bus ===
     */
    public BusManager ()
    {
        busHashMap = new HashMap<String, Bus>();
        setChanged();
        notifyObservers();
    }
    
    /**
     Notifie ses Observers.
     */
    public void start ()
    {
        setChanged();
        notifyObservers();
    }
    
    /**
     Ajoute un Bus au BusManager s'il n'existe pas déjà.
     === Nécessite la Classe Bus ===
     
     @param busName nom du Bus à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean createBus (String busName)
    {
        if(busName.isEmpty()) return false;
        if(busName.equals("")) return false;
        if(hasExistingBus(busName)) return false;
        
        busHashMap.put(busName, new Bus(busName));
        
        setChanged();
        notifyObservers();
        
        SerializationClass.save(this);
        
        return true;
    }
    
    /**
     Ajoute une Box à un Bus [busName] passé en paramètre
     
     @param busName    nom du Bus à modifier
     @param newBoxName nom de la Box à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean createBox (String busName, String newBoxName)
    {
        if(busName.isEmpty()) return false;
        if(newBoxName.isEmpty()) return false;
        if(busName.equals("")) return false;
        if(newBoxName.equals("")) return false;
        if(!busHashMap.containsKey(busName)) return false;
        
        Boolean reussi = busHashMap.get(busName).createBox(newBoxName);
        
        setChanged();
        notifyObservers();
        
        SerializationClass.save(this);
        
        return reussi;
    }
    
    /**
     Ajoute un String (message) dans la Box "default" du Bus [busName]
     
     @param busName           nom du Bus à modifier
     @param newMessageContent contenu du Message à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean emit (String busName, String newMessageContent)
    {
        if(busName.isEmpty()) return false;
        if(newMessageContent.isEmpty()) return false;
        if(busName.equals("")) return false;
        if(newMessageContent.equals("")) return false;
        if(!busHashMap.containsKey(busName)) return false;
        
        Boolean reussi = busHashMap.get(busName).emit(newMessageContent);
        
        setChanged();
        notifyObservers();
        
        SerializationClass.save(this);
        
        return reussi;
    }
    
    /**
     Ajoute un String (message) dans la Box [boxName]
     
     @param busName           nom du Bus à modifier
     @param boxName           nom de la Box dans laquelle le message doit être ajouté
     @param newMessageContent contenu du Message à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean emit (String busName, String boxName, String newMessageContent)
    {
        if(busName.isEmpty()) return false;
        if(boxName.isEmpty()) return false;
        if(newMessageContent.isEmpty()) return false;
        if(busName.equals("")) return false;
        if(boxName.equals("")) return false;
        if(newMessageContent.equals("")) return false;
        if(!hasExistingBus(busName)) return false;
        if(!hasExistingBox(busName, boxName)) return false;
        
        Boolean reussi = busHashMap.get(busName).emit(boxName, newMessageContent);
        
        setChanged();
        notifyObservers();
        
        SerializationClass.save(this);
        
        return reussi;
    }
    
    /**
     Permet de savoir si le BusManager contient déjà un Bus [busName]
     
     @param busName nom du Bus à rechercher
     @return True si le Bus existe déjà dans le BusManager
     */
    public Boolean hasExistingBus (String busName)
    {
        if(busName.isEmpty()) return false;
        if(busName.equals("")) return false;
        
        return busHashMap.containsKey(busName);
    }
    
    /**
     Permet de savoir si le BusManager contient déjà une Box [boxName] dans un Bus [busName]
     
     @param busName nom du Bus à rechercher
     @param boxName nom de la Box à rechercher
     @return True si la Box existe dans le Bus et que le Bus est dans le BusManager
     */
    public Boolean hasExistingBox (String busName, String boxName)
    {
        if(busName.isEmpty()) return false;
        if(boxName.isEmpty()) return false;
        if(busName.equals("")) return false;
        if(boxName.equals("")) return false;
        if(!hasExistingBus(busName)) return false;
        
        return getAllBoxNames(busName).contains(boxName);
    }
    
    /**
     Retourne l'ensemble des noms des Bus du BusManager
     
     @return la liste des noms des Bus
     */
    public Collection<String> getAllBusNames ()
    {
        return busHashMap.keySet();
    }
    
    /**
     Retourne l'ensemble des noms des Box d'un Bus [busName]
     
     @param busName nom du Bus à chercher
     @return la liste des noms des Box
     */
    public Collection<String> getAllBoxNames (String busName)
    {
        if(hasExistingBus(busName))
        {
            return busHashMap.get(busName).getBoxNames();
        }
        
        return null;
    }
    
    /**
     Retourne l'ensemble des Messages, tous Bus et toutes Box comprises.
     === Nécessite la Classe Bus ===
     
     @return la liste des Messages du BusManager
     */
    public Collection<String> getAllMessages ()
    {
        ArrayList<String> messageArrayList = new ArrayList<String>();
        
        for (Bus bus : busHashMap.values())
        {
            messageArrayList.addAll(bus.getAllMessages());
        }
        return messageArrayList;
    }
    
    /**
     Retourne l'ensemble des Messages, toutes Box comprises, d'un Bus [busName]
     
     @param busName nom du Bus à rechercher
     @return la liste des Messages du Bus
     */
    public Collection<String> getAllMessages (String busName)
    {
        if(busHashMap.containsKey(busName))
        {
            return busHashMap.get(busName).getAllMessages();
        }
        return null;
    }
    
    /**
     Retourne l'ensemble des Messages, d'une Box [boxName], d'un Bus [busName]
     
     @param busName nom du Bus à rechercher
     @param boxName nom de la Box à lire
     @return la liste des Messages de la Box
     */
    public Collection<String> getAllMessages (String busName, String boxName)
    {
        if(busHashMap.containsKey(busName))
        {
            return busHashMap.get(busName).getAllMessages(boxName);
        }
        return null;
    }
    
    /**
     Retourne un String qui correspond au BusManager
     === Nécessite la Classe Bus ===
     
     @return une version String du BusManager
     */
    public String toString ()
    {
        String nl = System.lineSeparator();
        
        String res = "";
        
        if(busHashMap.isEmpty())
        {
            res += "\n--> empty" + nl;
        }
        else
        {
            for (Bus b : busHashMap.values())
            {
                res += "\n--> " + b;
            }
        }
        
        return res + "\n===============\n\n";
    }
}