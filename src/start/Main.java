package start;

import bootcamp.Polynomial;

public class Main {



	public static void main(String ar[]) {

		//Polynomial poly=new Polynomial("3x^5 - 4x^2 - 14x^4");
		//Polynomial poly=new Polynomial("0");
		//Polynomial poly1=new Polynomial("-7x^5 - 14x^6 + 6x^4 + 100");

		Polynomial p1=new Polynomial("-x");
		Polynomial p2=new Polynomial("100");

		System.out.println(p1.multiply(p2));

	}

}
