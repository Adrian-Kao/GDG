import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

public class ColumnarDecrypt {
    
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
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("輸入密文: ");
        String ciphertext = sc.nextLine();
        
        System.out.print("輸入金鑰: ");
        String key = sc.nextLine();
        
        // 解密
        String plaintext = columnarDecrypt(ciphertext, key);
        System.out.println("原文: " + plaintext);
        
        sc.close();
    }
}
