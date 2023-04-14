import java.util.*;

public class Playfair {
    String chave;
    String texto;
    char[][] matriz = new char[5][5];

    //construtor da classe
    public Playfair(String chave, String texto) {
        // converter todos os caracteres para minúsculos
        this.chave = chave.toLowerCase();

        this.texto = texto.toLowerCase();
    }

    // essa função remove caracteres duplicados da chave
    public void limparChave() {
        //mantém a ordem de inserção dos caracteres
        LinkedHashSet<Character> set
                = new LinkedHashSet<Character>();

        String novaChave = "";

        for (int i = 0; i < chave.length(); i++)
            set.add(chave.charAt(i));

        Iterator<Character> it = set.iterator();

        while (it.hasNext())
            novaChave += (Character) it.next();

        chave = novaChave;
    }

    // função para gerar a tabela playfair cipher chave
    public void gerarChavecifrada() {
        Set<Character> set = new HashSet<Character>();

        for (int i = 0; i < chave.length(); i++) {
            if (chave.charAt(i) == 'j')
                continue;
            set.add(chave.charAt(i));
        }

        // remove caracteres repetidos da chave de cifra
        String tempchave = new String(chave);

        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;

            if (!set.contains(ch))
                tempchave += ch;
        }

        // cria a tabela chave de cifra
        for (int i = 0, idx = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matriz[i][j] = tempchave.charAt(idx++);

        System.out.println("Chave inserida na matriz:");

        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(matriz[i]));
    }

    // Funcão para pre processar o texto
    public String formataTexto() {
        String message = "";
        int len = texto.length();

        for (int i = 0; i < len; i++) {
            // se texto contém 'j', então
            // substituir por 'i'
            if (texto.charAt(i) == 'j')
                message += 'i';
            else
                message += texto.charAt(i);
        }

        // se dois caracteres consecutivos são iguais
        // então adicionar um 'x' no meio
        for (int i = 0; i < message.length(); i += 2) {
            if (message.charAt(i) == message.charAt(i + 1))
                message = message.substring(0, i + 1) + 'x'
                        + message.substring(i + 1);
        }

        // torna o tamanho do texto par
        if (len % 2 == 1)
            message += 'x'; // adiciona 'x' no final

        return message;
    }

    // função para dividir o texto em pares de caracteres
    public String[] formaPares(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++)
            pairs[i] = message.substring(cnt, cnt += 2);

        return pairs;
    }

    // função para descobrir a posição de um caractere na matriz chave
    public int[] getCharPos(char ch) {
        int[] chavePos = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (matriz[i][j] == ch) {
                    chavePos[0] = i;
                    chavePos[1] = j;
                    break;
                }
            }
        }
        return chavePos;
    }

    public String encMsg() {
        String message = formataTexto();
        String[] msgPares = formaPares(message);
        String encText = "";

        for (int i = 0; i < msgPares.length; i++) {
            char ch1 = msgPares[i].charAt(0);
            char ch2 = msgPares[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // se ambos os caracteres estiverem na mesma linha
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }

            // se ambos os caracteres estiverem na mesma coluna
            else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }

            // se ambos os caracteres estão em colunas diferentes
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // obter os caracteres cifrados a partir da chave
            encText = encText + matriz[ch1Pos[0]][ch1Pos[1]]
                    + matriz[ch2Pos[0]][ch2Pos[1]];
        }

        return encText;
    }

    public String decryptMessage(String encText) {
        String[] msgPares = formaPares(encText);
        String decText = "";

        for (int i = 0; i < msgPares.length; i++) {
            char ch1 = msgPares[i].charAt(0);
            char ch2 = msgPares[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            // se ambos os caracteres estiverem na mesma linha
            if (ch1Pos[0] == ch2Pos[0]) {
                ch1Pos[1] = (ch1Pos[1] + 4) % 5;
                ch2Pos[1] = (ch2Pos[1] + 4) % 5;
            }

            // se ambos os caracteres estiverem na mesma coluna
            else if (ch1Pos[1] == ch2Pos[1]) {
                ch1Pos[0] = (ch1Pos[0] + 4) % 5;
                ch2Pos[0] = (ch2Pos[0] + 4) % 5;
            }

            // se ambos os caracteres estão em colunas diferentes
            // trocar as colunas
            else {
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            // obter os caracteres cifrados a partir da chave
            // e adicioná-los ao texto decifrado
            decText = decText + matriz[ch1Pos[0]][ch1Pos[1]]
                    + matriz[ch2Pos[0]][ch2Pos[1]];
        }

        return decText;
    }
}
