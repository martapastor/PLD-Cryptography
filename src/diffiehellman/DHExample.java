package diffiehellman;

public class DHExample {

	public void init() {

        // 1.
        // These are Alice and Bob.
        // Alice and Bob want to chat securely.

        final DiffieHellman alice = new DiffieHellman();
        final DiffieHellman bob   = new DiffieHellman();

        // 2.
        // Alice and Bob generate public and private keys.

        alice.generateKeys();
        bob.generateKeys();


        // 3.
        // Alice and Bob exchange public keys with each other.

        alice.receivePublicKeyFrom(bob);
        bob.receivePublicKeyFrom(alice);

        // 4. 
        // Alice generates common secret key via using her private key and Bob's public key.
        // Bob generates common secret key via using his private key and Alice's public key.
        // Both secret keys are equal without TRANSFERRING. This is the magic of Diffie-Helman algorithm.

        alice.generateCommonSecretKey();
        bob.generateCommonSecretKey();

        // 5. 
        // Alice encrypts message using the secret key and sends to Bob

        final byte[] secretMessage = alice.encryptAndSendMessage("Bob! Guess who I am!", bob);

        // 6. 
        // Bob receives the important message and decrypts with secret common key.

        bob.tellTheMessage(secretMessage); // Encrypted message
        
        bob.receiveAndDecryptMessage(secretMessage);
        bob.tellTheMessage(); // Decrypted message
        
        // 7. 
        // Bob replies with another encrypted message using the secret key and sends it to Alice

        final byte[] secretMessageReply = bob.encryptAndSendMessage("Hi there, Alice! How do you do?", alice);

        // 8. 
        // Alice receives the reply and decrypts it with secret common key.

        alice.tellTheMessage(secretMessageReply); // Encrypted message
        
        alice.receiveAndDecryptMessage(secretMessageReply);
        alice.tellTheMessage(); // Decrypted message
    }
}
