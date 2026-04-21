RMI Tax Program  - Part B3
Step 1
•	Create a normal Java Application. (Eg. PartB3_RMI)
•	Add an Interface (TaxInterface) under Source Packages
public interface TaxInterface extends Remote 
{
    public double computeTax(double amount) throws RemoteException;
    	}
Step 2
Add a new Java Class (Calc_Tax.java)
Write the following code.
public class Calc_Tax extends UnicastRemoteObject implements TaxInterface
 {
    public Calc_Tax () throws RemoteException
    {
     super();   
    }

    public double computeTax(double amount) throws RemoteException {
    {
    	double tax = 0;
        	if (amount<=300000)
                		tax=0;
        	else if(amount<=600000)
            		tax = (amount-300000) * 0.05;
        	else if(amount<=900000)
            		tax =  15000 + (amount-600000)*0.1;
        	else if(amount<=1200000)
            		tax=45000 + (amount-900000)*0.15;
        	else if(amount<1500000)
            		tax = 90000 + (amount-1200000)*0.2;
        	else
            		tax = 90000 + 60000 + (amount-1500000)*0.3;
        	return tax;
    }
}

 
Step 3
Under Source Package add another Java Class (TaxServer.java)
Add the following code

public class TaxServer 
{
    public static void main(String[] args) 
    {
        try {
    
        	  Registry rg = LocateRegistry.createRegistry(33323);
            	  TaxInterface obj = new Calc_Tax();
            	  rg.rebind("TaxServer", obj);
            	  System.out.println("Tax Server is running...");
        	} 
catch (RemoteException e) 
{
            		System.out.println(e.toString());
        	}
    }
}


Step 4
Under Source Packages, add another new Java Class (TaxClient.java)

public class TaxClient {
    public static void main(String[] args) throws Exception
    {
   	Registry rg = LocateRegistry.getRegistry("localhost", 33323);
        	TaxInterface tx = (TaxInterface) rg.lookup("TaxServer");

        	Scanner data = new Scanner(System.in);
      	String wish = "Y";  double amt;

        	while (wish.equalsIgnoreCase("y")) 
{
            		System.out.println("Enter total amount");
            		amt = data.nextDouble();

            		System.out.println("Taxamount is = " + tx.computeTax(amt));

            		System.out.println("Enter wish - Y/N ");
            		wish = data.next();
        	}
    }     
}


