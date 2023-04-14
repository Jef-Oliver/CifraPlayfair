import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cifra Playfair");

        System.out.println("Digite a chave: ");
        String chave = sc.nextLine();
        
        System.out.println("Digite o texto a ser criptografado: ");
        String texto = sc.nextLine();

        System.out.println("Chave: " + chave);
        System.out.println("Texto: " + texto);

        Playfair pfc = new Playfair(chave, texto);
        pfc.limparChave();
        pfc.gerarChavecifrada();

        String encText = pfc.encMsg();
        System.out.println("Texto cifrado: " + encText);

        String decText = pfc.decryptMessage(encText);
        System.out.println("Texto descifrado: " + decText);
    }
}