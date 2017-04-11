package fr.unice.iut.info.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/*................................................................................................................................
 . Copyright (c)
 .
 . The Box	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:27
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 Classe qui définit la Box.
 Une Box est placée dans un Bus et contient des Messages.
 Une Box ne peux pas contenir deux Messages identiques.
 
 === Nécessite la Classe Message ===
 */
public class Box extends Observable implements Serializable
{
    final private String name;
    final private ArrayList<Message> messageArrayList;
    
    /**
     Constructeur de la Box.
     === Nécessite la Classe Box ===
 
     @param Name nom de la Box
     */
    public Box (String Name)
    {
        name = Name;
        messageArrayList = new ArrayList<Message>();
    }
    
    /**
     Cherche un Message dans la Box
     === Nécessite la Classe Message ===
     
     @param o objet à chercher
     @return retourne True si la Box contient o
     */
    public Boolean contains (Object o)
    {
        if(o == null) return false;
        if(!(o instanceof String)) return false;
        if(((String) o).isEmpty()) return false;
        if(o.equals("")) return false;
        
        for (Message m : messageArrayList)
        {
            if(m.equals(new Message((String) o)))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     Ajoute un message à la Box si elle ne le contient pas déjà.
     === Nécessite la Classe Message ===
     
     @param newMessageContent String à ajouter à la boite
     @return True si tout s'est bien passé
     */
    public Boolean emit (String newMessageContent)
    {
        if(newMessageContent.isEmpty()) return false;
        if(newMessageContent.equals("")) return false;
        if(contains(newMessageContent)) return false;
        
        messageArrayList.add(new Message(newMessageContent));
        return true;
    }
    
    /**
     Retourne tous les messages de la boite.
     === Nécessite la Classe Message ===
     
     @return la liste des messages de la boite
     */
    public Collection<String> getAllMessages ()
    {
        ArrayList<String> listStrings = new ArrayList<String>();
        
        for (Message m : messageArrayList)
        {
            listStrings.add(m.toString());
        }
        return listStrings;
    }
    
    /**
     Retourne un String qui correspond à la Box
     === Nécessite la Classe Message ===
     
     @return une version String de la Box
     */
    public String toString ()
    {
        String nl = "\n";
        
        String res = "Box : " + name + nl;
        
        if(messageArrayList.isEmpty())
        {
            res += "> empty";
        }
        else
        {
            for (Message m : messageArrayList)
            {
                res += "> " + m + nl;
            }
        }
        
        return res;
    }
    
}
