package fr.unice.iut.info;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/*................................................................................................................................
 . Copyright (c)
 .
 . The BusTest	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:23
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

public class BusTest
{
    private Bus busTest;
    
    @Before //Reinitialiser le Bus entre chaque Test.
    public void OnBeforeTests () throws Exception
    {
        busTest = new Bus("BusTest");
    }
    
    //region //================== Tests Bus ==================//
    
    @Test   //Lire toutes les Boites du Bus. => True
    public void getBoxNames () throws Exception
    {
        busTest.createBox("BoxTest");
        
        busTest.emit("MessageTest1");
        busTest.emit("BoxTest", "MessageTest2");
        
        assertEquals("[default, BoxTest]", busTest.getBoxNames().toString());
    }
    
    @Test   //Lire tous les Messages d'1 Bus. => True
    public void getAllMessages1 () throws Exception
    {
        busTest.createBox("BoxTest");
        
        busTest.emit("MessageTest1");
        Date d1 = new Date();
        busTest.emit("BoxTest", "MessageTest2");
        Date d2 = new Date();
        
        assertEquals("[Message from " + d1 + " : MessageTest1," + " Message from " + d2 + " : MessageTest2]",
                     busTest.getAllMessages().toString());
    }
    
    @Test   //Lire tous les Messages d'1 Box. => True
    public void getAllMessages2 () throws Exception
    {
        busTest.createBox("BoxTest");
        
        busTest.emit("MessageTest1");
        busTest.emit("BoxTest", "MessageTest2");
        
        assertEquals("[Message from " + new Date() + " : MessageTest2]", busTest.getAllMessages("BoxTest").toString());
    }
    
    @Test   //Lire Bus.toString(). => True
    public void BusToString () throws Exception
    {
        Bus bus1 = new Bus("Bus1");
        
        assertEquals("Bus : Bus1\n" + "-> Box : default\n" + "> empty\n", bus1.toString());
    }
    
    //endregion
    
    //region //================== Tests Box ==================//
    
    @Test   //Créer 2 Box différentes sur le bus. => True
    public void createBox1 () throws Exception
    {
        assertTrue(busTest.createBox("Box1") && busTest.createBox("Box2"));
    }
    
    @Test   //Créer 2 Box indentiques sur le bus. => False
    public void createBox2 () throws Exception
    {
        assertFalse(busTest.createBox("Box1") && busTest.createBox("Box1"));
    }
    
    @Test   //Créer 1 Box sans nom. => False
    public void createBox3 () throws Exception
    {
        assertFalse(busTest.createBox(""));
    }
    
    //endregion
    
    //region //================== Tests Message ==================//
    
    @Test   //Créer 1 Message dans 1 Box. => True
    public void emit1 () throws Exception
    {
        busTest.createBox("BoxTest");
        
        assertTrue(busTest.emit("BoxTest", "MessageTest1"));
    }
    
    @Test   //Créer 1 Message dans default. => True
    public void emit2 () throws Exception
    {
        assertTrue(busTest.emit("MessageTest1"));
    }
    
    @Test   //Créer 1 Message vide dans 1 Box. => False
    public void emit4 () throws Exception
    {
        busTest.createBox("BoxTest");
        
        assertFalse(busTest.emit("BoxTest", "")); // On ne peut pas emettre de message vide dans un boite.
    }
    
    @Test   //Créer 1 Message vide dans 1 default. => False
    public void emit3 () throws Exception
    {
        assertFalse(busTest.emit(""));
    }
    
    @Test   //Créer 2 Messages identiques dans 1 Box. => False
    public void emit5 () throws Exception
    {
        busTest.createBox("Box1");
        
        assertFalse(busTest.emit("Message1") && busTest.emit("Message1"));
    }
    
    @Test   //Créer 2 Messages différents dans 1 Box. => True
    public void emit6 () throws Exception
    {
        busTest.createBox("Box1");
        
        assertTrue(busTest.emit("Message1") && busTest.emit("Message2"));
    }
    
    //endregion
}