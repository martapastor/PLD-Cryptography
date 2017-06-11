package pohlighellman;

import static java.lang.Math.sqrt;

public class PohligHellman {

    static int noofPrimes = 0; 

    static int[] calculateX(int[] primefactors, int p, int alpha, int beta) {

        int i = 1;        //Guarda el primo actualmente en uso desde 1 hasta p-1
        int power = 0;    //Número de veces que i ha sido -1
        int q = 1;        //q = i^power
        int a, b;         //a = (p-1)/1; b = (p-1)/i
        int lhs_b;     	  //Valor de b en lhs ya que cambia en función de la potencia
        int lhs, rhs;     //Valores normales de lhs y rhs: lhs = (lhs_b^a)%p; rhs = (a^b)%p

        int[] final_X = new int[noofPrimes * 2];    //Valores finales computados de X para cada índice de un factor primo
        int l = -2;                                 //para final_X; 1º índice tiene valor x, 2º índice tiene el valor 
                                                    //del respectivo módulo

        int[] X = new int[primefactors.length];     //Array temporal para guardar los valores de X aún no computados

        for (int j = 0; primefactors[j] != 0; j++) {

            if (i != primefactors[j]) {
                l += 2;
                i = primefactors[j];
                power = 0;
            } else 
                power++;
           
            q = q * i;
            if (power == 0) {
                q = i;
                lhs_b = beta;
            } else 
                lhs_b = beta * (inverse_mod(alpha, X[j - 1], p)) % p;
            
            a = (p - 1) / q;
            b = (p - 1) / i;

            lhs = exponent_mod(lhs_b, a, p);
            rhs = exponent_mod(alpha, b, p);

            int k = 0;
            while (lhs != exponent_mod(rhs, k, p)) {
                k++;
            }
            X[j] = k;
            final_X[l] += ((Math.pow(primefactors[j], power)) * k);
            final_X[l + 1] = (int) ((Math.pow(i, power + 1)));
        }
        return final_X;
    }

    static int exponent_mod(int a, int b, int m) {

        //calculate a^b (mod m)
        int a1 = a % m;
        int p = 1;

        for (int i = 1; i <= b; i++) {
            p *= a1;
            p = (p % m);
        }
        return p;
    }

    static int inverse_mod(int alpha, int x, int p) {

        for (int i = 2; i < p; i++) {
            if ((alpha * i) % p == 1) {
                return i;
            }
        }

        return 0;
    }

    static int[] primeFactors(int n) {

        int[] primefactors = new int[n / 2];
        int index = 0;

        if (n % 2 == 0) {
            noofPrimes++;
        }
        while (n % 2 == 0) {
            primefactors[index++] = 2;
            n = n / 2;
        }

        for (int i = 3; i <= sqrt(n); i = i + 2) {
            if (n % i == 0) {
                noofPrimes++;
            }
            while (n % i == 0) {
                primefactors[index++] = i;
                n = n / i;
            }
            
        }

        if (n > 2) {
            primefactors[index++] = n;
            noofPrimes++;
        }

        return primefactors;
    }

    static boolean checkifPrime(int n) {
        int i = 2;
        while (i <= sqrt(n)) {
            if (n % i == 0) { //Si existe algún divisor para p disinto de 1 y de si mismo, entonces no es primo
                return false;
            }
            i++;
        }
        return true;
    }
}
