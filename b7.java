import java.util.*;

class PartB7 {
    public static void main(String[] args) {
        LinkedList<Integer> Flist = new LinkedList<Integer>();
        LinkedList<Integer> Slist = new LinkedList<Integer>();
        Iterator itr;
        Scanner data = new Scanner(System.in);
        char choice = 'x';
        int pos, i, n, num, fpos, spos;
        do {
            System.out.println("Menu");
            System.out.println("a. Insert some Elements");
            System.out.println("b. Swap Elements");
            System.out.println("c. Iterate List in Reverse");
            System.out.println("d. Compare Two Lists");
            System.out.println("e. Convert Array to List");
            System.out.println("x. Exit");
            System.out.println("Enter your choice");
            choice = data.next().toLowerCase().charAt(0);

            switch (choice) {
                case 'a':
                    System.out.println("Enter the position");
                    pos = data.nextInt();
                    if (pos < 0 || pos > Flist.size())
                        System.out.println("Error... Enter a valid number ");
                    else {
                        System.out.println("Enter no. of elements required");
                        n = data.nextInt();
                        System.out.println("Enter the required elements ");
                        for (i = 0; i < n; i++) {
                            num = data.nextInt();
                            Flist.add(pos + i, num);
                        }
                        System.out.println("Elements added successfully");
                        System.out.println("The contents of the list is ");
                        itr = Flist.iterator();
                        while (itr.hasNext())
                            System.out.print(itr.next() + "  ");
                    }
                    System.out.println();
                    break;

                case 'b':
                    System.out.println("Original List is " + Flist);
                    System.out.println("Enter position of elements to be swapped");
                    System.out.println("Enter First position");
                    fpos = data.nextInt();
                    System.out.println("Enter Second position");
                    spos = data.nextInt();
                    if (fpos < 0 && spos <= fpos)
                        System.out.println("Error.. use proper  positions ");
                    else if ((fpos > Flist.size()) || (spos > Flist.size()))
                        System.out.println("Enter proper value for position ");
                    else {
                        int n1 = Flist.get(fpos); 
                        int n2 = Flist.get(spos);
                        Flist.set(spos, n1);
                        Flist.set(fpos, n2);
                        System.out.println("Elements are swapped");
                        System.out.println("List after swapping is " + Flist);
                    }
                    break;

                case 'c':
                    System.out.println("Original List is " + Flist);
                    System.out.print("Reversed List is : ");
                    itr = Flist.descendingIterator();
                    while (itr.hasNext())
                        System.out.print(itr.next() + "  ");
                    break;

                case 'd':
                    Slist = (LinkedList<Integer>) Flist.clone();
                    Slist.add(123);
                    if (Flist.equals(Slist))
                        System.out.println("Lists are equal");
                    else
                        System.out.println("Lists are not equal");
                    break;

                case 'e':
                    ArrayList<Integer> Alist = new ArrayList<Integer>(Flist);
                    System.out.println("Elements in the array list are ");
                    System.out.print(Alist + "   ");
                    break;

                case 'x':
                    System.out.println("Quitting....");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wrong Choice...");

            }
        } while (true);
    }
}
