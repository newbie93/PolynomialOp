package bootcamp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Term implements Comparable {

	public int degree;
	public int coeff;
	public char varaible;
	@Override
	public int compareTo(Object term) {
		// TODO Auto-generated method stub
		return this.degree-((Term)term).degree;
	}

	@Override
	public String toString() {
		//String str=
		if(coeff==0)
			return "0";
		if(degree==1) {
			if(coeff>0)
				return "+"+coeff+"x";
			return coeff+"x";
		}
		if(coeff>0)
			return "+"+coeff+"x"+"^"+degree;
		return coeff+"x"+"^"+degree;
	}

	public Term add(Term t) {
		if(t.degree!=this.degree)
			return null;
		Term newTerm=new Term();
		newTerm.coeff=this.coeff+t.coeff;
		newTerm.degree=this.degree;
		newTerm.varaible=this.varaible;
		return newTerm;
	}

	public Term(int degree, int coeff, char var) {
		this.degree=degree;
		this.coeff=coeff;
		this.varaible=var;
	}
	public Term() {

	}

	public static Term generateTerm(String str) {
		//System.out.println(str);
		Term term=new Term();
		term.coeff=1;
		term.varaible='x';
		term.degree=0;
		String[]tokens=str.split("\\^");
		if(tokens.length>1)
			term.degree=Integer.parseInt(tokens[1]);
		if(tokens[0].charAt(tokens[0].length()-1)=='x') {
			if(term.degree==0)
				term.degree=1;
			if(tokens[0].length()==1)
				return term;
			else if(tokens[0].length()==2 && tokens[0].charAt(0)=='-') {
				term.coeff=-1;
				return term;
			}
			term.coeff=Integer.parseInt(tokens[0].substring(0, tokens[0].length()-1));
		}
		else
			term.coeff=Integer.parseInt(tokens[0].substring(0, tokens[0].length()));
		return term;
	}

}

public class Polynomial {

	public ArrayList<Term>poly;

	private static ArrayList<Term> generateList(String inpPol) {
		String[]list=inpPol.split("\\+");
		ArrayList<Term>termList=new ArrayList<>();
		for(String str:list) {
			if(!str.equals(""))
				termList.add(Term.generateTerm(str));
		}
		return termList;
	}

	public Polynomial(String inpPol) {
		inpPol=preProcess(inpPol);
		poly=generateList(inpPol);
		Collections.sort(poly);
	}

	public Polynomial(ArrayList<Term>list) {
		this.poly=list;
	}

	@Override
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
		return newPoly;
	}

	public Polynomial sum(Polynomial p) {

		HashMap<Integer, Integer>map=new HashMap<>();
		ArrayList<Term>termList=new ArrayList<>();
		int newCoeff;
		for(Term t:this.poly)
			map.put(t.degree, t.coeff);
		String poly="",temp="";
		for(Term t:p.poly) {
			newCoeff=t.coeff;
			if(map.containsKey(t.degree)) {
				newCoeff+=map.get(t.degree);
				map.remove(t.degree);
			}
			termList.add(new Term(t.degree,newCoeff,'x'));
		}
		for(int degree:map.keySet())
			termList.add(new Term(degree,map.get(degree),'x'));
		return new Polynomial(termList);
	}

	public Polynomial subtract(Polynomial p) {

		HashMap<Integer, Integer>map=new HashMap<>();
		ArrayList<Term>termList=new ArrayList<>();
		int newCoeff;
		for(Term t:this.poly)
			map.put(t.degree, t.coeff);
		String poly="",temp="";
		for(Term t:p.poly) {
			newCoeff=-t.coeff;
			if(map.containsKey(t.degree)) {
				newCoeff+=map.get(t.degree);
				map.remove(t.degree);
			}
			termList.add(new Term(t.degree,newCoeff,'x'));
		}
		for(int degree:map.keySet())
			termList.add(new Term(degree,map.get(degree),'x'));
		return new Polynomial(termList);
	}

	public Polynomial multiply(Polynomial p) {
		HashMap<Integer,Integer>degreeMap=new HashMap<>();
		HashMap<Integer, Integer>finalMap=new HashMap<>();
		ArrayList<Term>list=new ArrayList<>();
		int newCoeff,newDegree;
		for(Term t:this.poly)
		degreeMap.put(t.degree, t.coeff);
		for(Term t:p.poly) {
			for(int degree:degreeMap.keySet()) {
				newCoeff=t.coeff*degreeMap.get(degree);
				newDegree=t.degree+degree;
				if(finalMap.containsKey(newDegree))
					finalMap.put(newDegree, newCoeff+finalMap.get(newDegree));
				else
					finalMap.put(newDegree, newCoeff);
			}
		}
		for(int degree:finalMap.keySet())
			list.add(new Term(degree,finalMap.get(degree),'x'));
		return new Polynomial(list);
	}

}
