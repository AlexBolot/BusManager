package fr.unice.iut.info.service;

import java.io.*;

/*................................................................................................................................
 . Copyright (c)
 .
 . The SerializationClass	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:27
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

public abstract class SerializationClass
{
    private static final String savesLocation = "SavedManager.xml";
    
    public static void save (Object o)
    {
        ObjectOutputStream oos = null;
        try
        {
            final FileOutputStream fichier = new FileOutputStream(savesLocation);
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(o);
            oos.flush();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(oos != null)
                {
                    oos.flush();
                    oos.close();
                }
            }
            catch (final IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public static Object read ()
    {
        ObjectInputStream ois = null;
        Object o = null;
        try
        {
            final FileInputStream fichier = new FileInputStream(savesLocation);
            ois = new ObjectInputStream(fichier);
            o = ois.readObject();
            System.out.println(o);
        }
        catch (final java.io.FileNotFoundException fnfe)
        {
            System.out.println("Pas de précédentes sauvegarde");
        }
        catch (final InvalidClassException ice)
        {
            System.out.println("SavedManager.xml ne contient pas la bonne version de BusManager");
        }
        catch (final IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch (final ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        finally
        {
            try
            {
                if(ois != null)
                {
                    ois.close();
                }
            }
            catch (final IOException ex)
            {
                ex.printStackTrace();
                
            }
        }
        return o;
    }
}