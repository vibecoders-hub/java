Netbeans->New Project->Java->Java Application
B4 package(default)
package b4;
public class B4 {
    public static void main(String[] args) {
    }
}
B4->new Java Interface->Simple
package b4;
import java.rmi.*;
public interface Simple extends Remote{
    double CI(double p,double t,double r)throws RemoteException;
}
B4->new Java Class->Siserver
package b4;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class Siserver extends UnicastRemoteObject implements Simple{
   public Siserver()throws RemoteException{
        super();
    }
    @Override
    public double CI(double p,double t,double r)throws RemoteException{
        return (p*t*r)/100;
    } 
}
B4->new Java Class->Startserver
package b4;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Startserver {
    public static void main(String args[]) throws RemoteException, AlreadyBoundException {
        Siserver si = new Siserver();
        Registry reg = LocateRegistry.createRegistry(18888);
        reg.bind("Simple Interest", si);
        System.out.println("Server is Started ..");  
    } 
}
B4->new Java Class->Siclient
package b4;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry.*;
import java.rmi.registry.Registry;
import java.util.Scanner;
public class Siclient {
    public static void main(String args[])throws RemoteException, NotBoundException
    {
        Registry reg=LocateRegistry.getRegistry(18888);
        Simple sim=(Simple)reg.lookup("Simple Interest");
        Scanner sc=new Scanner(System.in);
        double p,t,r;
        String ans="n";
        do{
            System.out.println("Principle : ");
            p=sc.nextDouble();
            System.out.println("Time : ");
            t=sc.nextDouble();
            System.out.println("Rate : ");
            r=sc.nextDouble();
            System.out.println("Simple Interest : "+sim.CI(p,t,r));
            System.out.println("Do you want to continue [y/n]");
            sc.nextLine();
            ans=sc.nextLine();
        }while(ans.toLowerCase().charAt(0)=='y');
    }
}


  
