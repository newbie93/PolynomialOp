package bootcamp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

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
}

public class Polynomial {
	
	public ArrayList<Term>poly;
	
	/**/
	
	private static Term generateTerm(String str) {
		Term term=new Term();
		term.coeff=1;
		term.varaible='\0';
		term.degree=0;
		String[]tokens=str.split("^");
		if(tokens.length>1)
			term.degree=Integer.parseInt(tokens[1]);
		tokens=str.split("");
		
		return term;
	}
	
	private static ArrayList<Term> generateList(String inpPol) {
		String[]list=inpPol.split("+");
		ArrayList<Term>termList=new ArrayList<>();
		for(String str:list)
			termList.add(generateTerm(str));
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
			str+=t.coeff+t.varaible+"^"+t.degree+" ";
		return str;
	}
	
	private static String preProcess(String poly) {
		String newPoly="";
		if(poly.charAt(0)!='-' || poly.charAt(0)!='+')
			newPoly="+"+poly;
		for(int i=0;i<poly.length();i++) {
			if(poly.charAt(i)==' ')
				continue;
			if(poly.charAt(i)=='-')
				newPoly+="+";
			newPoly+=poly.charAt(i);
		}
		return newPoly;
	}

}
