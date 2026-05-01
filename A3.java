        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        int choice;
        
        do{
            System.out.println("1.Add 2.Sort 3.Replace 4.Remove 5.Display 6.InsertBetween 7.Exit");
            choice = sc.nextInt();
            
            switch(choice){
                case 1:
                    int n = sc.nextInt();
                    list.add(n);
                    break;
                case 2:
                    Collections.sort(list);
                    System.out.println(list);
                    break;
                case 3:
                    int oldval = sc.nextInt();
                    if(list.contains(oldval)){
                        int newval = sc.nextInt();
                        int idx = list.indexOf(oldval);
                        list.set(idx,newval);
                    }
                    break;
                case 4:
                    int val = sc.nextInt();
                    list.remove((Integer)val);
                    break;
                case 5:
                    System.out.println(list);
                    break;
                case 6:
                    int num = sc.nextInt();
                    int a = sc.nextInt();
                    int b = sc.nextInt();
                    
                    if(list.contains(a) && list.contains(b)){
                        if(list.indexOf(b) - list.indexOf(a) == 1){
                            list.add(list.indexOf(a) + 1,num);
                        }
                    }
                    break;
                case 7:
                    System.exit(0);
            }
        }while(true);
