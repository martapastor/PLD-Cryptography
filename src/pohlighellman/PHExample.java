package pohlighellman;

import java.util.Scanner;

import pohlighellman.PohligHellman;

public class PHExample {

	public void init() {
		Scanner scan = new Scanner(System.in);
	        
        //Variables de entrada
        int p = 0;
        int a = 0, b = 0;
        
        /*
         * Ejemplos: 
         * 	p = 17		|
         * 	a = 3		|	x = 6
         * 	b = 14 		|
         * 
         * 	p = 17		|
         * 	a = 3		|	x = 19
         * 	b = 10		|
         * 
         */

        int[] primefactors = new int[p / 2]; // Factores primos de p-1 
        
        System.out.println("Introducir los valores a y b tal que b = a^x (mod p)");
        
        System.out.println("Introducir el valor de p: ");
        p = scan.nextInt();
        if (!PohligHellman.checkifPrime(p)) { //checking if p is prime
            System.out.println("El número no es primo.");
            return;
        }
        System.out.println("Introducir valor de a: ");
        a = scan.nextInt();
        System.out.println("Introducir valor de b: ");
        b = scan.nextInt();
        
        //Obtenemos los valores primos hata p-1
        System.out.println("Calculando los factores primos hasta p-1...");
        primefactors = PohligHellman.primeFactors(p-1);

        System.out.println("Calculando los valores de X mod m...");
        int[] final_X = PohligHellman.calculateX(primefactors, p, a, b); //Calculamos los valores de X mod m

        //Encontramos la X usando el Teorema Chino del Resto (CRT, Chinese Remain Theorem)
        System.out.println("Calculando el Teorema Chino del Resto...");
        CRT crt = new CRT();
        int[] result = crt.computeCRT(final_X);

        //Mostramos los resultados:
        System.out.println("Por el Teorema Chino del Resto: x = " + result[0] + "(mod " + result[1] + ")");
        System.out.println(b + " = " + a + "^" + result[0] + "(mod " + result[1] + ")");
	}
}
