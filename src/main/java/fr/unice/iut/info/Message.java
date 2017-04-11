package fr.unice.iut.info;

import java.io.Serializable;
import java.util.Date;
import java.util.Observable;

/*................................................................................................................................
 . Copyright (c)
 .
 . The Message	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:23
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 Classe qui définit la Box.
 Une Box est placée dans un Bus et contient des Messages.
 Une Box ne peux pas contenir deux Messages identiques.
 
 === Ne nécessite aucune Classe ===
 */

public class Message extends Observable implements Serializable
{
    final private String content;
    final private Date date;
    
    /**
     Constructeur du Message.
     Ajoute le contenu et la date à l'instant t, de manière définitive.
     Le contenu d'un Message n'est pas modifiable.
     
     @param Content contenu du Message
     */
    public Message (String Content)
    {
        content = Content;
        date = new Date();
    }
    
    /**
     Compare un Objet avec (this).
     
     @param o objet à comparer
     @return retourne True si (this) a le même contenu que o
     */
    @Override
    public boolean equals (Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(!(o instanceof Message))
        {
            return false;
        }
        
        Message message = (Message) o;
        
        return content != null ? content.equals(message.content) : message.content == null;
        
    }
    
    /**
     Retourne un String qui correspond au
     Contenu et à la date du Message
     
     @return une version String du Message
     */
    public String toString ()
    {
        return "Message from " + date + " : " + content;
    }
}
