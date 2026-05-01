static String toggle(String s){
        StringBuilder res = new StringBuilder();
        for(char ch : s.toCharArray()){
            if(Character.isUpperCase(ch)){
                res.append(Character.toLowerCase(ch));
            }else if(Character.isLowerCase(ch))
                res.append(Character.toUpperCase(ch));
            else
                res.append(ch);
            
        }
        return res.toString();
    }
    static String swap(String s){
        StringBuilder res = new StringBuilder();
        String[] words = s.split("\\s+");
        for(String w: words){
            if(w.length() % 2== 0){
                for(int i = 0;  i < w.length(); i+=2){
                    res.append(w.charAt(i+1));
                    res.append(w.charAt(i));
                }
                res.append(" ");
            }
        }
        
        return res.toString().trim();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        
        System.out.println(toggle(str));
        System.out.println(swap(str));
    }
