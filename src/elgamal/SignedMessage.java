package elgamal;

import java.math.BigInteger;

public class SignedMessage {

		private BigInteger s; //La signature du message
		private BigInteger r; //g^k mod p
		
		public SignedMessage(BigInteger s, BigInteger r){
			this.s = s;
			this.r = r;
		}
		
		public SignedMessage(){}
		
		public void set_s (BigInteger s) {
			this.s = s;
			}
		public void set_r (BigInteger r) {
			this.r = r;
		}
		
		public BigInteger get_s (){
			return this.s;
		}
		public BigInteger get_r(){
			return this.r;
		}
}
	
