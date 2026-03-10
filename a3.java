import java.util.*;

class PartA3
{
public static void main(String args[])
{
	int choice=0,n,x,z; Scanner data=new Scanner(System.in);
	Iterator <Integer> itr;
	ArrayList<Integer> alist = new ArrayList<Integer>();

	do
	{
		System.out.println("     Main Menu     ");
		System.out.println("-------------------");
		System.out.println("1.Adding Elements");
		System.out.println("2.Sorting Elements");
		System.out.println("3.Replace an element with another");
		System.out.println("4.Removing an Element");
		System.out.println("5.Displaying all Elements");
		System.out.println("6.Adding an Element between two elements");
		System.out.println("7.Exit");
		System.out.println("Enter your choice.... ");
		choice=data.nextInt();

		switch(choice)
		{
		 	case 1: 	System.out.println("Enter a number to add");
					n=data.nextInt();
					alist.add(n); break;

			case 2: 	Collections.sort(alist);
        					System.out.println("Sorted List is ");
				         itr = alist.iterator();
        					while(itr.hasNext())
            					System.out.print(itr.next()+"\t");
        					System.out.println();
        					break;
        
			case 3: 	System.out.println("Enter value to be  replaced");
  					n=data.nextInt();
  					if(alist.contains(n))
    					{
      						System.out.println("Enter new value ");
      						z=data.nextInt();
      						int index=alist.indexOf(n);
                					alist.set(index,z);
      						System.out.println("Replacement completed");
    					}
   					else
     						System.out.println("Entered value not found");
                				break;

			case 4: 	System.out.println("Enter the element to remove");
					n=data.nextInt();
					if(alist.contains(n))
						alist.remove((Integer)n);
					else
						System.out.println("Value not found");
					break;

			case 5:	if (alist.isEmpty())
         						System.out.println("List is empty");
   					else
	   				{
						System.out.println("Contents of the List is");
                					itr = alist.iterator();
                					while(itr.hasNext())
                   						System.out.print(itr.next()+"\t");
            				}
               				System.out.println();
          					break;
			case 6:
					System.out.println("Enter the Element to be Added");
        					int num, num1,num2;
					num=data.nextInt();
        					System.out.println("Enter the First Element");
					num1=data.nextInt();
					System.out.println("Enter the Second Element");
					num2=data.nextInt();
					if(alist.contains(num1) && alist.contains(num2))
	 				{
						if(alist.indexOf(num2)-alist.indexOf(num1)==1)
		 				{
							alist.add(alist.indexOf(num1)+1,num);
							System.out.println("Item Added");
		 				}
						else
		   					System.out.println("numbers are not succussive");
	  				}
					else
						System.out.println("One of the elements is not Exist");
					break;

			case 7: 	System.exit(0); break;

			default:	System.out.println("Wrong choice...");
		}
	} while(true);
    }			
 }	



