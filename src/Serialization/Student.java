package Serialization;

import Img.StandarImage;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Student  extends StandarImage implements Serializable
{


    public Student(int score, String name)
    {
        super("",0,0);
        Scoce = score;
        Name = name;



    }

    public String getName() {
        return Name;
    }

    public int getScoce() {
        return Scoce;
    }

    public void setScoce(int scoce) {
        Scoce = scoce;
    }

    int Scoce;

    public void setName(String name) {
        Name = name;
    }

    String Name;
}
