package fr.unice.iut.info;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

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

public class MessageTest
{
    @Test   //Verifier si 2 Messages identiques sont identiques. => True
    public void equals1 () throws Exception
    {
        Message messageTest1 = new Message("MessageTest");
        Message messageTest2 = new Message("MessageTest");
        
        Assert.assertTrue(messageTest1.equals(messageTest2));
    }
    
    @Test   //Verifier si 2 Messages différents sont identiques. => False
    public void equals2 () throws Exception
    {
        Message messageTest1 = new Message("MessageTest1");
        Message messageTest2 = new Message("MessageTest2");
        
        Assert.assertFalse(messageTest1.equals(messageTest2));
    }
    
    @Test   //Lire Message.toSting(). => True
    public void MessageToString () throws Exception
    {
        Message messageTest1 = new Message("MessageTest");
        
        Assert.assertEquals("Message from " + new Date() + " : MessageTest", messageTest1.toString());
    }
}