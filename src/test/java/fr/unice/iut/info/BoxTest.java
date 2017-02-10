package fr.unice.iut.info;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

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

public class BoxTest
{
    private Box boxTest;
    
    @Before
    public void OnBeforeTests () throws Exception
    {
        boxTest = new Box("BoxTest");
    }
    
    //region //================== Tests Box ==================//
    
    @Test   //Tester si 1 Box contient bien 1 Message existant. => True
    public void contains1 () throws Exception
    {
        boxTest.emit("MessageTest1");
        assertTrue(boxTest.contains("MessageTest1"));
    }
    
    @Test   //Tester si 1 Box contient bien 1 Message inexistant. => False
    public void contains2 () throws Exception
    {
        //Le MessageTest1 n'a pas été créé.
        assertFalse(boxTest.contains("MessageTest2"));
    }
    
    @Test   //Tester si 1 Box contient bien 1 Message vide. => False
    public void contains3 () throws Exception
    {
        assertFalse(boxTest.contains(""));
    }
    
    @Test   //Lire tous les messages de la Box. => True
    public void getAllMessages () throws Exception
    {
        boxTest.emit("MessageTest1");
        Date d1 = new Date();
        boxTest.emit("MessageTest2");
        Date d2 = new Date();
        boxTest.emit("MessageTest3");
        Date d3 = new Date();
        
        assertEquals("[Message from " + d1 + " : MessageTest1, Message from " + d2 + " : MessageTest2, Message from " + d3 + " : MessageTest3]",
                     boxTest.getAllMessages().toString());
    }
    
    @Test   //Lire Box.toString(). => True
    public void BoxToString () throws Exception
    {
        assertEquals("Box : BoxTest\n" + "> empty", boxTest.toString());
    }
    
    //endregion
    
    //region //================== Tests Message ==================//
    
    @Test   //Créer 1 Message. => True
    public void emit1 () throws Exception
    {
        assertTrue(boxTest.emit("MessageTest"));
    }
    
    @Test   //Créer 1 Message Vide. => False
    public void emit2 () throws Exception
    {
        assertFalse(boxTest.emit(""));
    }
    
    @Test   //Créer 2 Messages identiques. => False
    public void emit3 () throws Exception
    {
        assertFalse(boxTest.emit("Message1") && boxTest.emit("Message1"));
    }
    
    @Test   //Créer 2 Messages différents. => True
    public void emit4 () throws Exception
    {
        assertTrue(boxTest.emit("Message1") && boxTest.emit("Message2"));
    }
    
    //endregion
}