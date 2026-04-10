StudentModel.java
public class StudentModel {   
    private String sname;
    private int rollno,mark1,mark2,mark3;

    public StudentModel(String sname, int rollno, int mark1, int mark2, int mark3) {
        this.sname = sname;
        this.rollno = rollno;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public int getMark1() {
        return mark1;
    }

    public void setMark1(int mark1) {
        this.mark1 = mark1;
    }

    public int getMark2() {
        return mark2;
    }

 
    public void setMark2(int mark2) {
        this.mark2 = mark2;
    }

    public int getMark3() {
        return mark3;
    }

    public void setMark3(int mark3) {
        this.mark3 = mark3;
    }
    
    public double getAvg()
    {
        double avg=(mark1+mark2+mark3)/3;
        return avg;
    }
    public String getResult()
    {
        String res="";
        double avg=getAvg();
        
        if(mark1<35 || mark2<35||mark3<35)
            res="Fail";       
        if(avg>=75)
            res="Distinction";
        else if(avg>=60)
            res="First Class";
        else if(avg>=50)
            res="Second class";
        else if(avg>=35)
            res="Third Class";
        else
            res="Fail";            
        return res;
    }
    
    public String getGrade()
    {
        String grd="";
        double avg=getAvg();
       
        if(avg>=90)
            grd="A";
        else if(avg>=80)
            grd="B";
        else if(avg>=70)
            grd="C";
        else if(avg>=60)
            grd="D";
        else
            grd="E";
        
        return grd;
        
    }
}


StudentView.java
public class StudentView 
{
    public void showResult(String nm, int rno, int m1, int m2, int m3, double avg, String res, String grd)
    {
        	System.out.println("Name\tRoll Number\tMarks-1\tMarks-2\tMarks-3\tAverage
                         \tResult\tGrade");
System.out.println(nm+"\t"+rno+"\t\t"+m1+"\t"+m2+"\t"+m3+"\t"+avg+"\t"+res+"\t"+grd);
     }
}


StudentController.java
public class StudentController {   
    private StudentModel model;
    private StudentView view;

    public StudentController(StudentModel model, StudentView view) {
              this.model = model;
this.view = view;
    }

     
 
public void updateView()
    {
        view.showResult (model.getSname(), model.getRollno(), model.getMark1(), 
                model.getMark2(), model.getMark3(), 
                model.getAvg(),model.getResult(), model.getGrade());
      }
}


MVCApplications.java

import java.util.*;

public class MVCApplications {
    public static void main(String[] args) {
        int rlno,m1,m2,m3;
        String stname;
        Scanner  data=new Scanner(System.in);
        
        System.out.println("Enter roll number");
        rlno=data.nextInt();
        data.nextLine();
        
        System.out.println("Enter Student Name ");
        stname=data.nextLine();
        
        System.out.println("Enter Marks in 3 subjects ");
        m1=data.nextInt();
        m2=data.nextInt();
        m3=data.nextInt();
        
        StudentModel sm=new StudentModel(stname, rlno, m1, m2, m3);
        StudentView sv=new StudentView();
        StudentController sc=new StudentController(sm,sv);
        sc.updateView();
     }   
}

