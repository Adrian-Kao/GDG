import java.util.Scanner;
public class encrypt {
     public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                boolean isUpper = Character.isUpperCase(ch);
                int base = isUpper ? 'A' : 'a';

                int p = ch - base;
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int c = (p + k) % 26;

                ciphertext.append((char) (c + base));
                keyIndex++;
            } else {
                ciphertext.append(ch); // 非字母原樣保留
            }
        }
        return ciphertext.toString();
    }

    // 解密
    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                boolean isUpper = Character.isUpperCase(ch);
                int base = isUpper ? 'A' : 'a';

                int c = ch - base;
                int k = key.charAt(keyIndex % key.length()) - 'A';
                int p = (c - k + 26) % 26;

                plaintext.append((char) (p + base));
                keyIndex++;
            } else {
                plaintext.append(ch); // 非字母原樣保留
            }
        }
        return plaintext.toString();
    }

    // 測試
    public static void main(String[] args) {
        System.out.print("輸入明文: ");
        Scanner sc= new Scanner(System.in);
        String plaintext = sc.nextLine();
        System.out.println("輸入密鑰");
        String key = sc.nextLine();

        String encrypted = encrypt(plaintext, key);
        System.out.println("加密後: " + encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println("解密後: " + decrypted);
    }


    
}
