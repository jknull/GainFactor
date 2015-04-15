package me.gainfactor2.www.gainfactor2;
import java.util.Hashtable;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
// a look up table for gains

public class LUT implements Serializable{
	public Hashtable<String,Food> table;
	private static final long serialVersionID = 0L;

	public LUT(){
		table = new Hashtable<String,Food>();
	}


	public void add(String s, Food f){
		table.put(s,f);
	}


	public void remove(String s){
		table.remove(s);
	}
	public Food get(String s){
		
		return table.get(s);
	}

	public String toString(){
		return table.toString();

	}

	
	public static void main (String [] args)throws Exception{
		LUT lut = new LUT();
		// Food f = new Food(5,70,53,6,false);

		// lut.add("food1",f);
		// lut.add("food2",f);
		// lut.add("food3",f);
		// lut.add("food4",f);
		// lut.add("food5",f);
		// lut.add("food6",f);
		// FileOutputStream fileOut = new FileOutputStream("LUT.ser");
		// ObjectOutputStream out= new ObjectOutputStream(fileOut);
		// out.writeObject(lut);
		// fileOut.close();

//		FileInputStream fileIn = new FileInputStream("LUT.ser");
//		ObjectInputStream in = new ObjectInputStream(fileIn);
//		lut =(LUT)in.readObject();
//
//		System.out.println(lut);


	}






}