package fr.unice.iut.info;

import fr.unice.iut.info.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/*................................................................................................................................
 . Copyright (c)
 .
 . The MessageTest	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:27
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

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