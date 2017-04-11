package fr.unice.iut.info;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/*................................................................................................................................
 . Copyright (c)
 .
 . The BusManagerTest	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:23
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

public class BusManagerTest
{
    
    private BusManager manager;
    
    @Before //Reinitialiser le manager entre chaque Test.
    public void OnBeforeTests () throws Exception
    {
        manager = new BusManager();
    }
    
    //region //================== Tests BusManager ==================//
    
    //region //--------- Tests HasBus ---------//
    
    @Test   //Verifier présence d'1 Bus existant. => True
    public void hasExistingBus1 () throws Exception
    {
        manager.createBus("Bus1");
        
        assertTrue(manager.hasExistingBus("Bus1"));
    }
    
    @Test   //Verifier présence d'1 Bus inexistant. => False
    public void hasExistingBus2 () throws Exception
    {
        //Le Bus1 n'a pas été créé.
        assertFalse(manager.hasExistingBus("Bus1"));
    }
    
    @Test   //Verifier présence d'1 Bus vide. => False
    public void hasExistingBus3 () throws Exception
    {
        assertFalse(manager.hasExistingBus(""));
    }
    
    //endregion
    
    //region //--------- Tests HasBox ---------//
    
    @Test   //Verifier présence d'1 Box existante d'1 Bus existant. => True
    public void hasExistingBox1 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        
        assertTrue(manager.hasExistingBox("Bus1", "Box1"));
    }
    
    @Test   //Verifier présence d'1 Box existante d'1 Bus inexistant. => False
    public void hasExistingBox2 () throws Exception
    {
        //Le Bus1 n'a pas été créé.
        manager.createBox("Bus1", "Box1");
        
        assertFalse(manager.hasExistingBox("Bus1", "Box1"));
    }
    
    @Test   //Verifier présence d'1 Box inexistante d'1 Bus existant. => False
    public void hasExistingBox3 () throws Exception
    {
        manager.createBus("Bus1");
        //La Box1 n'a pas été créée.
        assertFalse(manager.hasExistingBox("Bus1", "Box1"));
    }
    
    @Test   //Verifier présence d'1 Box inexistante d'1 Bus inexistant. => False
    public void hasExistingBox4 () throws Exception
    {
        //Le Bus1 n'a pas été créé.
        //La Box1 n'a pas été créée.
        assertFalse(manager.hasExistingBox("Bus1", "Box1"));
    }
    
    @Test   //Verifier présence d'1 Box vide. => False
    public void hasExistingBox5 () throws Exception
    {
        manager.createBus("Bus1");
        assertFalse(manager.hasExistingBox("Bus1", ""));
    }
    
    @Test   //Lire BusManager.toString(). => True
    public void BusManagerToString () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        manager.emit("Bus1", "Box1", "Message1");
        Date d1 = new Date();
        manager.emit("Bus1", "Message2");
        Date d2 = new Date();
        
        assertEquals("\n" + "--> Bus : Bus1\n" + "-> Box : default\n" + "> Message from " + d2 + " : Message2\n" + "\n" + "-> Box : Box1\n" + "> Message from " + d1 + " : Message1\n" + "\n" + "\n" + "===============" + "\n" + "\n",
                     manager.toString());
    }
    
    //endregion
    
    //endregion
    
    //region //================== Tests Bus =========================//
    
    @Test   //Créer 2 Bus différents sur le manager. => True
    public void createBus1 () throws Exception
    {
        assertTrue(manager.createBus("Bus1") && manager.createBus("Bus2"));
    }
    
    @Test   //Créer 2 Bus identiques sur le manager. => False
    public void createBus2 () throws Exception
    {
        assertFalse(manager.createBus("Bus1") && manager.createBus("Bus1"));
    }
    
    @Test   //Créer 1 Bus sans nom. => False
    public void createBus3 () throws Exception
    {
        assertFalse(manager.createBus(""));
    }
    
    //endregion
    
    //region //================== Tests Box =========================//
    
    @Test   //Créer 2 Box différentes sur 1 Bus. => True
    public void createBox1 () throws Exception
    {
        manager.createBus("Bus1");
        assertTrue(manager.createBox("Bus1", "Box1") && manager.createBox("Bus1", "Box2"));
    }
    
    @Test   //Créer 2 Box identiques sur 1 Bus. => False
    public void createBox2 () throws Exception
    {
        manager.createBus("Bus1");
        assertFalse(manager.createBox("Bus1", "Box1") && manager.createBox("Bus1", "Box1"));
    }
    
    @Test   //Créer 2 Box identiques sur 2 Bus différents. => True
    public void createBox3 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBus("Bus2");
        assertTrue(manager.createBox("Bus1", "Box1") && manager.createBox("Bus2", "Box1"));
    }
    
    @Test   //Créer 1 Box sur 1 Bus innexistant. => False
    public void createBox4 () throws Exception
    {
        //Le Bus1 n'a pas été créé.
        assertFalse(manager.createBox("Bus1", "Box1"));
    }
    
    @Test   //Créer 1 Box sur un Bus sans nom. => False
    public void createBox5 () throws Exception
    {
        assertFalse(manager.createBox("", "Box1"));
    }
    
    @Test   //Créer 1 Box sans nom. => False
    public void createBox6 () throws Exception
    {
        manager.createBus("Bus1");
        assertFalse(manager.createBox("Bus1", ""));
    }
    
    //endregion
    
    //region //================== Tests Message =====================//
    
    @Test   //Créer 1 Message sur 1 Box d'un Bus => True
    public void emit1 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        
        assertTrue(manager.emit("Bus1", "Box1", "Message1"));
    }
    
    @Test   //Créer 1 Message sur default d'1 Bus. => True
    public void emit2 () throws Exception
    {
        manager.createBus("Bus1");
        
        assertTrue(manager.emit("Bus1", "Message1"));
    }
    
    @Test   //Créer 2 Messages différents sur 1 Box d'1 Bus. => True
    public void emit3 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        
        assertTrue(manager.emit("Bus1", "Box1", "Message1") && manager.emit("Bus1", "Box1", "Message2"));
    }
    
    @Test   //Créer 2 Messages identiques sur 1 Box d'1 Bus. => False
    public void emit4 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        
        assertFalse(manager.emit("Bus1", "Box1", "Message1") && manager.emit("Bus1", "Box1", "Message1"));
    }
    
    @Test   //Créer 1 Message vide dans 1 Box d'1 Bus. => False
    public void emit5 () throws Exception
    {
        manager.createBus("Bus1");
        manager.createBox("Bus1", "Box1");
        
        assertFalse(manager.emit("Bus1", "Box1", ""));
    }
    
    @Test   //Créer 1 Message vide dans default d'1 Bus. => False
    public void emit6 () throws Exception
    {
        manager.createBus("Bus1");
        
        assertFalse(manager.emit("Bus1", ""));
    }
    
    @Test   //Lire tous les messages d'1 Bus, d'1 Boite. => True
    public void getAllMessages () throws Exception
    {
        manager.createBus("Bus1");
        
        manager.createBox("Bus1", "Boite1");
        
        manager.emit("Bus1", "Boite1", "Message1");
        Date d1 = new Date();
        manager.emit("Bus1", "Boite1", "Message2");
        Date d2 = new Date();
        
        assertEquals("[Message from " + d1 + " : Message1," + " Message from " + d2 + " : Message2]",
                     manager.getAllMessages("Bus1", "Boite1").toString());
    }
    
    //endregion
}