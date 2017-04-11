package fr.unice.iut.info.controller;

import java.util.Collection;
import java.util.Scanner;

/*................................................................................................................................
 . Copyright (c)
 .
 . The CLI	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 11/04/17 23:27
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 Class de Command Line Interface
 
 Elle sert à :
 -> Afficher des Informations sur System.out
 -> Lire des Instructions Utilisateur sur System.in
 
 Cette classe est autonome et ne sert que d'Entrée/Sortie.
 */
public class CLI
{
    private Scanner sc = new Scanner(System.in);
    
    public String getInstructions ()
    {
        System.out.println("------- COMMANDES ----------");
        System.out.println("creer Bus (c)");
        System.out.println("creer Boite (b)");
        System.out.println("lire messages (l)");
        System.out.println("emettre un nouveau message (e)");
        System.out.println("stop (s)");
        System.out.println("Que voulez-vous faire :");
        String s = sc.nextLine();
        System.out.println("Vous avez saisi : " + s);
        return s;
    }
    
    public String getEmitInstructions ()
    {
        System.out.println("--- Options ---");
        System.out.println("Emettre message dans Bus (c)");
        System.out.println("Emettre message dans Boîte (b)");
        System.out.println("Que voulez-vous faire :");
        String instructions = sc.nextLine();
        System.out.println("Vous avez saisi : " + instructions);
        return instructions;
    }
    
    public String getReadInstructions ()
    {
        System.out.println("--- Options ---");
        System.out.println("Lire tous les messages (a)");
        System.out.println("Lire message dans Bus (c)");
        System.out.println("Lire message dans Boîte (b)");
        System.out.println("Que voulez-vous faire :");
        String instructions = sc.nextLine();
        System.out.println("Vous avez saisi : " + instructions);
        return instructions;
    }
    
    public String getLine (String message)
    {
        if(!message.equals(""))
        {
            System.out.println(message);
        }
        return sc.nextLine();
    }
    
    public void print (String s)
    {
        System.out.println(s);
    }
    
    public void printList (Collection<String> SetNames)
    {
        for (String s : SetNames)
        {
            System.out.println("\t" + s);
        }
    }
    
    public void printListBus (Collection<String> SetBusName)
    {
        System.out.println("--- Bus disponibles ---");
        printList(SetBusName);
        System.out.println("==> Nom du bus ?");
    }
    
    public void printListBox (Collection<String> SetBoxNames)
    {
        System.out.println("--- Boites disponibles ---");
        printList(SetBoxNames);
        System.out.println("==> Nom de la boite ?");
    }
    
    public void printMessages (Collection<String> CollectionMessages)
    {
        int i = 1;
        if(CollectionMessages == null)
        {
            System.out.println("\t Pas de messages ");
        }
        else
        {
            for (String s : CollectionMessages)
            {
                System.out.println("\t" + i + "-\t" + s);
                i++;
            }
        }
    }
}