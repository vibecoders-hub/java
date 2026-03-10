import java.util.*;

class PartA2
{
	public static void main(String args[])
	{
            int n,i,count=1,slarge,ssmall;
            Scanner data=new Scanner(System.in);               
            System.out.println("Enter the Size of array:");
            n=data.nextInt();
            if(n<3)
            {
                System.out.println("Enter the array with atleast 3 elements");
                return;
            }
            Integer a[]=new Integer[n];     // autoboxing 
            System.out.println("Enter any " + n + " elements:");
            for(i=0;i<n;i++)
            {
                a[i]=data.nextInt();
                if(i>0 && a[i]==a[i-1])
                    count++;
            }
            if(count==n)
            {
                System.out.println("Array values must be unique ");
                System.exit(0);
            }
            else
            {
                Arrays.sort(a);
                ssmall=a[1];            // unboxing
                slarge=a[a.length-2];   // unboxing
                System.out.println("Second largest Element:"+slarge);
                System.out.println("Second smallest Element:"+ssmall);
            }
	}
}

