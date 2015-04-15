package me.gainfactor2.www.gainfactor2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
//food object 
public class Food implements Serializable{
	//gainfactor, pricepoint,price calories, protein, mass
	private double GF;
	private double PP;
	private double price;
	private double cals;
	private double protein;
	private double mass;
    private double totalmass;
	//boolean representing lbs if true grams if false
	boolean lbs;
	//constant for lbs to grams
	private final static double LBSTOG = 453.59237;
	// blank constructor
	public Food(){
		this(Double.NaN,Double.NaN,Double.NaN,Double.NaN,Double.NaN,false);
	}
	//constructor with price
	public Food(double price, double cals, double mass, double totalmass, double protein, boolean lbs){
		if(lbs){
			this.GF = calculateGains(cals,mass*LBSTOG,protein);
			this.PP = gainsPerPrice(price,cals,mass*LBSTOG,totalmass*LBSTOG,protein);
		}else{
			this.GF = calculateGains(cals,mass,protein);
			this.PP = gainsPerPrice(price,cals,mass,totalmass,protein);
		}
		if(price==Double.NaN){
			this.PP=Double.NaN;
		}
		if(cals==Double.NaN){
			this.GF=Double.NaN;
		}
		this.price = price;
		this.cals = cals;
		this.mass = mass;
		this.protein = protein;

	}
    public double getGF(){
        return this.GF;
    }
    public double getPP(){
        return this.PP;
    }
	//constructor without price
	public Food(double cals, double mass, double totalmass,double protein,boolean lbs){
		this(Double.NaN,cals,mass,totalmass,protein,lbs);
	}
	// calculates gainfactor
	private static double calculateGains(double cals, double mass, double protein){
		return ((cals/mass)*(1+Math.sqrt(protein/mass)));
	}

	//calculates pricepoint
	private static double gainsPerPrice(double price, double cals, double mass, double totalmass, double protein){
		return (calculateGains(cals,mass,protein)*totalmass)/price;
	}

	public String toString(){
		String s = this.GF+ ","+this.PP+ ","+this.price+ ","+this.cals+ ","+this.mass+ ","+this.protein;
		return s;
	}
	
	//test
	public static void main(String[] args) throws Exception{
//		System.out.println("price,cals,mass,protein");
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String input = br.readLine();
//		String[]inputs = input.split(",");
//		System.out.println("testing food constructors");
//		Food a = new Food();
//		Food a2 = new Food(Double.parseDouble(inputs[0]),Double.parseDouble(inputs[1]),Double.parseDouble(inputs[2]),Double.parseDouble(inputs[3]),true);
//		Food a3 = new Food(Double.parseDouble(inputs[1]),Double.parseDouble(inputs[2]),Double.parseDouble(inputs[3]),true);
//		Food a4 = new Food(Double.parseDouble(inputs[0]),Double.parseDouble(inputs[1]),Double.parseDouble(inputs[2]),Double.parseDouble(inputs[3]),false);
//		Food a5 = new Food(Double.parseDouble(inputs[1]),Double.parseDouble(inputs[2]),Double.parseDouble(inputs[3]),false);
//
//		System.out.println(a);
//		System.out.println(a2);
//		System.out.println(a3);
//		System.out.println(a4);
//		System.out.println(a5);



	}



}