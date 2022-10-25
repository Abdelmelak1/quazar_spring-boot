package com.supportportal.fitnesse;

public class Calculator {
    private int first,second;

    public void setFirst(int first)
    {
        this.first=first;
    }

    public void setSecond(int second)
    {
        this.second=second;
    }

    public int addition()
    {
        return (first+second);
    }

    public int minus()
    {
        return (first-second);
    }

    public int multiply()
    {
        return (first*second);
    }

    public float divide()
    {
        return (first/second);
    }

    public static void main(String[] args) {
//                Calculator c = new Calculator();
//                System.out.println(c);
        System.out.println("hello from Intellij");
    }
}

