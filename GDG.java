import java.awt.*;
import java.awt.image.BufferedImage;

public class GDG {
    static String text = "歡迎";
    static String fontName = "Microsoft JhengHei";
    static int fontSize =48;       
    static int padding = 0;         
    static int blockX = 1;          
    static int blockY = 2;         
    static double fillThreshold = 0.3; 
    static String onChar  = "*";
    static String offChar = " ";

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g0 = tmp.createGraphics();
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        g0.setFont(font);
        FontMetrics fm = g0.getFontMetrics();
        int textW = fm.stringWidth(text);
        int textH = fm.getAscent() + fm.getDescent();
        g0.dispose();

        int imgW = textW + padding * 2;
        int imgH = textH + padding * 2;
        BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgW, imgH);
        g.setColor(Color.BLACK);
        g.setFont(font);
        int baseX = padding;
        int baseY = padding + fm.getAscent();
        g.drawString(text, baseX, baseY);
        g.dispose();

        
        int minX = imgW, maxX = -1;
        for (int y = 0; y < imgH; y++) {
            for (int x = 0; x < imgW; x++) {
                int rgb = img.getRGB(x, y) & 0xFFFFFF;
                if (rgb != 0xFFFFFF) { 
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                }
            }
        }
        if (maxX == -1) { 
            System.out.println(text); 
            return;
        }
        
        minX = Math.max(0, (minX / blockX) * blockX);
        maxX = Math.min(imgW - 1, ((maxX / blockX) * blockX) + (blockX - 1));

        
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < imgH; y += blockY) {
            for (int x = minX; x <= maxX; x += blockX) {
                int blackCount = 0, total = 0;
                for (int dy = 0; dy < blockY && y + dy < imgH; dy++) {
                    for (int dx = 0; dx < blockX && x + dx < imgW; dx++) {
                        int rgb = img.getRGB(x + dx, y + dy);
                        int r = (rgb >> 16) & 0xff, gc = (rgb >> 8) & 0xff, b = rgb & 0xff;
                        int lum = (int)(0.2126 * r + 0.7152 * gc + 0.0722 * b);
                        if (lum < 200) blackCount++;
                        total++;
                    }
                }
                double ratio = (total == 0) ? 0 : (blackCount / (double) total);
                sb.append(ratio >= fillThreshold ? onChar : offChar);
            }
            sb.append('\n'); 
        }

        System.out.println(sb.toString()); 
    }
}
