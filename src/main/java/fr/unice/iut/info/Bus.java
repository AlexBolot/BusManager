package fr.unice.iut.info;

import java.io.Serializable;
import java.util.*;

/*................................................................................................................................
 . Copyright (c)
 .
 . The Bus	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:23
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 Classe qui définit le Bus.
 Un Bus est placée dans un BusManager et contient des Box.
 Un Bus ne peux pas contenir deux Box identiques.
 
 === Nécessite la Classe Box ===
 */
public class Bus extends Observable implements Serializable
{
    final private String               name;
    final private HashMap<String, Box> boxHashMap;
    
    /**
     Constructeur du Bus.
     Crée une Box "default".
     === Nécessite la Classe Box ===
     
     @param Name nom du Bus
     */
    public Bus (String Name)
    {
        name = Name;
        boxHashMap = new HashMap<String, Box>();
        boxHashMap.put("default", new Box("default"));
    }
    
    /**
     Ajoute une Box au Bus si elle n'existe pas déjà.
     === Nécessite la Classe Box ===
     
     @param newBoxName Nom de la Box à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean createBox (String newBoxName)
    {
        if(newBoxName.isEmpty()) return false;
        if(newBoxName.equals("")) return false;
        if(boxHashMap.containsKey(newBoxName)) return false;
        
        boxHashMap.put(newBoxName, new Box(newBoxName));
        return true;
    }
    
    /**
     Ajoute un String (message) dans la Box "default" du Bus
     
     @param newMessageContent contenu du Message à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean emit (String newMessageContent)
    {
        if(newMessageContent.isEmpty()) return false;
        if(newMessageContent.equals("")) return false;
        
        return boxHashMap.get("default").emit(newMessageContent);
    }
    
    /**
     Ajoute un String (message) dans la Box [boxName]
     
     @param boxName           nom de la Box dans laquelle le message doit être ajouté
     @param newMessageContent contenu du Message à ajouter
     @return True si tout s'est bien passé
     */
    public Boolean emit (String boxName, String newMessageContent)
    {
        if(boxName.isEmpty()) return false;
        if(boxName.equals("")) return false;
        if(newMessageContent.isEmpty()) return false;
        if(newMessageContent.equals("")) return false;
        if(!boxHashMap.containsKey(boxName)) return false;
        
        return boxHashMap.get(boxName).emit(newMessageContent);
    }
    
    /**
     Retourne la liste des noms des boites du Bus sous forme de String
     
     @return liste des noms des boites
     */
    public Set<String> getBoxNames ()
    {
        return boxHashMap.keySet();
    }
    
    /**
     Retourne tous les messages du Bus, toutes Box incluses
     === Nécessite la Classe Box ===
     
     @return liste des String (message)
     */
    public Collection<String> getAllMessages ()
    {
        ArrayList<String> res = new ArrayList<String>();
        for (Box b : boxHashMap.values())
        {
            res.addAll(b.getAllMessages());
        }
        return res;
    }
    
    /**
     Retourne tous les messages d'une Box [boxName] du Bus
     
     @param boxName nom de la Box à lire
     @return liste des String (message)
     */
    public Collection<String> getAllMessages (String boxName)
    {
        if(boxHashMap.containsKey(boxName))
        {
            return boxHashMap.get(boxName).getAllMessages();
        }
        return null;
    }
    
    /**
     Retourne un String qui correspond au Bus
     === Nécessite la Classe Box ===
     
     @return une version String du Bus
     */
    public String toString ()
    {
        String nl = "\n";
        
        String res = "Bus : " + name + nl;
        
        if(boxHashMap.isEmpty())
        {
            res += "-> empty" + nl;
        }
        else
        {
            for (Box b : boxHashMap.values())
            {
                res += "-> " + b + nl;
            }
        }
        
        return res;
    }
    
}
