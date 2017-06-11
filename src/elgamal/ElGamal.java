/**/

package elgamal;


import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.security.SecureRandom;

public class ElGamal {

	public static Key GenerateKeysElGamal(BigInteger a, int nbBits){
		Random rand = new SecureRandom();
		boolean validKey = false;
		boolean validGenerator = false;
		BigInteger bigTwo = new BigInteger("2");
		Key keyK = new Key(a,nbBits);

		BigInteger cont = BigInteger.ZERO;

		while (validKey != true){

			//Comenzamos buscando un número primo p = 2*q + 1 mayor que 'a' siendo 'q' primo también.

			keyK.set_q(BigInteger.probablePrime(keyK.get_nbBits()-1, rand));
			//Verificamos que  2*q + 1 es mayor que 'a':
			while ((((keyK.get_q().add(keyK.get_q())).add(BigInteger.ONE)).compareTo(keyK.get_a())) == -1){
				keyK.set_q(BigInteger.probablePrime(keyK.get_nbBits()-1, rand));
				System.out.println((keyK.get_q().add(keyK.get_q())).add(BigInteger.ONE));
			}
			//Construimos 'p' tal que p = 2*q + 1
			keyK.set_p((keyK.get_q().add(keyK.get_q())).add(BigInteger.ONE));

			//Comprobamos que 'p' es primo.
			//El valor del test de prueba es tal que el número es considera primo con una probabilidad de 1-1/(2^certeza)
			//Con una certeza 20 tenemos una posibilidad entre 1 millón de que el número no sea primo.
			if (keyK.get_p().isProbablePrime(20)){
				validKey = true;
			}
		}

		//A continuación intentamos encontrar un generador g > 1
		keyK.set_g(BigInteger.ONE);
		while (validGenerator != true){
			cont = BigInteger.ZERO;
			keyK.set_g(keyK.get_g().add(BigInteger.ONE));

			//Comprobamos si (g ^ q) mod p = 1:
			if ((keyK.get_g().modPow(keyK.get_q(),keyK.get_p())).compareTo(BigInteger.ONE) == 0 && (keyK.get_g().modPow(bigTwo,keyK.get_p())).compareTo(BigInteger.ONE) != 0){ // ==0 car la fonction retourne 0 quand il y a égalité des expressions
				validGenerator = true;
			}
		}

		keyK.set_y(keyK.get_g().modPow(keyK.get_a(), keyK.get_p()));

		return keyK;
	}

	public static EncryptedMessage EncrypterElGamal (String m, Key keyK){
		m = m.toUpperCase();

		Random rand = new SecureRandom();
		EncryptedMessage C = new EncryptedMessage();
		C.set_c("");
		BigInteger b = new BigInteger(keyK.get_nbBits(), rand);

		String M = "";
		char charBuffer;
		BigInteger longueurMessage = BigInteger.ZERO;
		BigInteger bigCont = BigInteger.ZERO;
		String stringBuffer;
		BigInteger bigIntBuffer;
		BigInteger nbChiffresDansP = BigInteger.ZERO;

		//Tranformamos el mensaje 'm' a encriptar en una cadena de código ASCII en un BigNumber.
		m += "#";
		while (m.charAt(0) != "#".charAt(0)){
			charBuffer = m.charAt(0);
			m = m.substring(1);
			M += (int)charBuffer;
		}

		//Determinamos la longitud del mensaje que va a codificarse una vez que se ha convertido a código ASCII:
		String copieM = M;
		copieM += "#";
		while (copieM.charAt(0) != "#".charAt(0)){
			longueurMessage = longueurMessage.add(BigInteger.ONE);
			copieM = copieM.substring(1);
		}

		//Buscamos el tamaño de los grupos de cifras à encriptar.
		String stringP = keyK.get_p().toString();
		stringP += "#";

		while (stringP.charAt(0) != "#".charAt(0)){
			nbChiffresDansP = nbChiffresDansP.add(BigInteger.ONE);
			stringP = stringP.substring(1);
		}
		BigInteger parametre;
		String blocEncode;
		String stringBlocEncode;
		BigInteger nbChiffresBloc;

		//Tratamos el mensaje:
		while(longueurMessage.compareTo(BigInteger.ZERO) != 0){
			bigCont = BigInteger.ZERO; //restablecemos a cero el contador de caracteres
			stringBuffer = ""; //búfer de volcado


			if (longueurMessage.compareTo(nbChiffresDansP) == -1){
				parametre = longueurMessage;
			}
			else {
				parametre = nbChiffresDansP.subtract(BigInteger.ONE);
			}


			//Reemplazamos el búfer con el número de cifras siguientes al parámetro de la función
			while (bigCont.compareTo(parametre) == -1){
				stringBuffer += M.charAt(0);
				M = M.substring(1);
				bigCont = bigCont.add(BigInteger.ONE);
			}
			//Búfer que contiene el valor decimal de una cadena de caracteres de tamaño p-1
			bigIntBuffer = new BigInteger(stringBuffer);

			// El programa pasa a la etapa de encriptar el bloque que está siendo procesado y añade el resultado a C
			// Cálculo del bloque: M x (y^b mod p) = M x (g^a mod p)^b mod p = M x (g^ab mod p)

			blocEncode = ((bigIntBuffer.multiply(keyK.get_y().modPow(b, keyK.get_p()))).mod(keyK.get_p())).toString();

			//Buscamos el tamaño de los bloques ecnriptados
			//SI el bloque es más pequeño que el número de cifras que componen el número p, completamos los huecos con 0
			stringBlocEncode = blocEncode.toString();
			stringBlocEncode += "#";
			nbChiffresBloc = BigInteger.ZERO;
			//Contamos el número de cifras en el bloque
			while (stringBlocEncode.charAt(0) != "#".charAt(0)){
				nbChiffresBloc = nbChiffresBloc.add(BigInteger.ONE);
				stringBlocEncode = stringBlocEncode.substring(1);
			}

			//Si es necesario, ajustamos los 0
			bigCont = BigInteger.ZERO;
			while (bigCont.compareTo(nbChiffresDansP.subtract(nbChiffresBloc)) == -1){
				bigCont = bigCont.add(BigInteger.ONE);
				blocEncode = "0" + blocEncode;
				}

			//Añadimos el bloque a la cadena C
			C.set_c(C.get_c() + blocEncode);
			longueurMessage = longueurMessage.subtract(parametre);
		}
		//Calculamos Y = g^b mod p
		C.set_Y(keyK.get_g().modPow(b, keyK.get_p()));
		return C;
	}

	public static String DecrypterElGamal(EncryptedMessage C, Key keyK){
		String c = C.get_c();
		String m = "";
		String M = "";
		

		return m;
	}

	public static SignedMessage SignElGamal (String m, Key keyK){
		Random rand = new SecureRandom();
		SignedMessage mSigne = new SignedMessage();
		

		return mSigne;
	}

	public static boolean VerifyElGamalSignature(String m, SignedMessage rs, Key keyK){
		return true;
	}

 	public static void init() {

		
	}
}
