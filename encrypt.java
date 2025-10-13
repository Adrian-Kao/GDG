import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

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

    // ========== 第二層加密：列置換加密 ==========
    
    // 第二層加密
    public static String columnarEncrypt(String text, String key) {
        String upperKey = key.toUpperCase();
        int numCols = upperKey.length();
        int numRows = (int) Math.ceil((double) text.length() / numCols);
        
        // 建立排序索引
        Integer[] order = new Integer[numCols];
        for (int i = 0; i < numCols; i++) {
            order[i] = i;
        }
        Arrays.sort(order, Comparator.comparing(i -> upperKey.charAt(i)));
        
        // 建立矩陣並填充
        char[][] matrix = new char[numRows][numCols];
        int index = 0;
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (index < text.length()) {
                    matrix[r][c] = text.charAt(index++);
                } else {
                    matrix[r][c] = 'X'; // 填充字符
                }
            }
        }
        
        // 按照密鑰順序讀取列
        StringBuilder result = new StringBuilder();
        for (int col : order) {
            for (int r = 0; r < numRows; r++) {
                result.append(matrix[r][col]);
            }
        }
        
        return result.toString();
    }

    // 第二層解密
    public static String columnarDecrypt(String ciphertext, String key) {
        String upperKey = key.toUpperCase();
        int numCols = upperKey.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numCols);
        
        // 建立排序索引
        Integer[] order = new Integer[numCols];
        for (int i = 0; i < numCols; i++) {
            order[i] = i;
        }
        Arrays.sort(order, Comparator.comparing(i -> upperKey.charAt(i)));
        
        // 建立矩陣並按列填充
        char[][] matrix = new char[numRows][numCols];
        int index = 0;
        for (int col : order) {
            for (int r = 0; r < numRows; r++) {
                if (index < ciphertext.length()) {
                    matrix[r][col] = ciphertext.charAt(index++);
                }
            }
        }
        
        // 按行讀取
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                result.append(matrix[r][c]);
            }
        }
        
        // 移除填充字符
        return result.toString().replaceAll("X+$", "");
    }

    // 輸入端
    public static void main(String[] args) {
        System.out.print("輸入明文: ");
        Scanner sc = new Scanner(System.in);
        String plaintext = sc.nextLine();
        System.out.println("輸入第一層密鑰");
        String key1 = sc.nextLine();
        System.out.println("輸入第二層密鑰");
        String key2 = sc.nextLine();

        // 第一層加密
        String encrypted = encrypt(plaintext, key1);
        System.out.println("第一層加密後: " + encrypted);

        // 第二層加密
        String doubleEncrypted = columnarEncrypt(encrypted, key2);
        System.out.println("第二層加密後: " + doubleEncrypted);

        // 第二層解密
        String firstDecrypt = columnarDecrypt(doubleEncrypted, key2);
        System.out.println("第一層解密後: " + firstDecrypt);

        // 第一層解密
        String decrypted = decrypt(firstDecrypt, key1);
        System.out.println("完全解密後: " + decrypted);
        
        sc.close();
    }
}