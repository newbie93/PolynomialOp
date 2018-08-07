package bootcamp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Term implements Comparable {
	
	public int degree;
	public int coeff;
	public char varaible;
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getCoeff() {
		return coeff;
	}

	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}

	public char getVaraible() {
		return varaible;
	}

	public void setVaraible(char varaible) {
		this.varaible = varaible;
	}
	
	@Override
	public int compareTo(Object term) {
		// TODO Auto-generated method stub
		return this.degree-((Term)term).degree;
	}
	
	@Override
	public String toString() {
		//String str=
		if(coeff>0)
			return "+"+coeff+"x"+"^"+degree;
		return coeff+"x"+"^"+degree;
	}
}

public class Polynomial {
	
	public ArrayList<Term>poly;
	
	/**/
	
	private static Term generateTerm(String str) {
		//System.out.println(str);
		Term term=new Term();
		term.coeff=1;
		term.varaible='x';
		term.degree=0;
		String[]tokens=str.split("\\^");
		if(tokens.length>1)
			term.degree=Integer.parseInt(tokens[1]);
		if(tokens[0].charAt(tokens[0].length()-1)!='x')
			term.coeff=Integer.parseInt(tokens[0]);
		else
			term.coeff=Integer.parseInt(tokens[0].substring(0, tokens[0].length()-1));
		return term;
	}
	
	private static ArrayList<Term> generateList(String inpPol) {
		String[]list=inpPol.split("\\+");
		ArrayList<Term>termList=new ArrayList<>();
		for(String str:list) {
			if(!str.equals(""))
				termList.add(generateTerm(str));
		}
		return termList;
	}
	
	public Polynomial(String inpPol) {
		inpPol=preProcess(inpPol);
		poly=generateList(inpPol);
		Collections.sort(poly);
	}
	
	public String toString() {
		String str="";
		for(Term t:poly)
			str+=t.toString();
		return str;
	}
	
	private static String preProcess(String poly) {
		String newPoly="";
		if(poly.charAt(0)!='-' || poly.charAt(0)!='+')
			newPoly="+";
		for(int i=0;i<poly.length();i++) {
			if(poly.charAt(i)==' ')
				continue;
			if(poly.charAt(i)=='-')
				newPoly+="+";
			newPoly+=poly.charAt(i);
		}
		//System.out.println(""+newPoly);
		return newPoly;
	}
	
	public Polynomial sum(Polynomial p) {
		HashMap<Integer, Integer>map=new HashMap<>();
		
		for(Term t:this.poly)
			map.put(t.degree, t.coeff);
		String poly="",temp="";
		for(Term t:p.poly) {
			if(map.containsKey(t.degree)) {
				if(t.degree==0)
					temp="+"+(map.get(t.degree)+t.coeff)+"";
				else
					temp="+"+(map.get(t.degree)+t.coeff)+"x^"+t.degree;
				map.remove(t.degree);
			}
			else {
					if(t.degree==0)
						temp="+"+t.coeff;
					else
						temp="+"+t.toString();
				}
			poly+=temp;
		}
		for(int degree:map.keySet()) {
			if(degree>0)
				poly+="+"+map.get(degree)+"x^"+degree;
			else
				poly+="+"+map.get(degree);
		}
		System.out.println(poly);
		return new Polynomial(poly);
	}
	
	public Polynomial subtract(Polynomial p) {
		HashMap<Integer, Integer>map=new HashMap<>();
		
		for(Term t:this.poly)
			map.put(t.degree, t.coeff);
		String poly="",temp="";
		for(Term t:p.poly) {
			if(map.containsKey(t.degree)) {
				if(t.degree==0)
					temp="+"+(map.get(t.degree)-t.coeff)+"";
				else
					temp="+"+(map.get(t.degree)-t.coeff)+"x^"+t.degree;
				map.remove(t.degree);
			}
			else {
				if(t.degree==0) {
					if(t.coeff<0)
						temp=""+Math.abs(t.coeff);
					else
						temp="-"+t.coeff;
				}
				else {
					if(t.coeff<0) {
						t.coeff=Math.abs(t.coeff);
						temp=t.toString();
					}
					else
						temp="-"+t.toString();
				}
			}
			poly+=temp;
		}
		for(int degree:map.keySet()) {
			if(degree>0)
				poly+="+"+map.get(degree)+"x^"+degree;
			else
				poly+="+"+map.get(degree);
		}
		System.out.println(poly);
		return new Polynomial(poly);
	}
	
	public static void main(String ar[]) {

		Polynomial poly=new Polynomial("3x^5 - 4x^2 + 4x^4");
		Polynomial poly1=new Polynomial("7x^5 - 14x^6 + 6x^4 + 100");
		
		
		
		System.out.println(poly.subtract(poly1));
		
	}

}
