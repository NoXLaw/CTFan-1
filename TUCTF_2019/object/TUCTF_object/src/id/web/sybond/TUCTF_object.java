package id.web.sybond;

/**
 * Solution to 'object' reverse challenge
 *
 * @author Bondan Sumbodo
 */
public class TUCTF_object {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte[] data = new byte[]{
            (byte) 0xfd, (byte) 0xff, (byte) 0xd3, (byte) 0xfd, (byte) 0xd9, (byte) 0xa3,
            (byte) 0x93, (byte) 0x35, (byte) 0x89, (byte) 0x39, (byte) 0xb1, (byte) 0x3d,
            (byte) 0x3b, (byte) 0xbf, (byte) 0x8d, (byte) 0x3d, (byte) 0x3b, (byte) 0x37,
            (byte) 0x35, (byte) 0x89, (byte) 0x3f, (byte) 0xeb, (byte) 0x35, (byte) 0x89,
            (byte) 0xeb, (byte) 0x91, (byte) 0xb1, (byte) 0x33, (byte) 0x3d, (byte) 0x83,
            (byte) 0x37, (byte) 0x89, (byte) 0x39, (byte) 0xeb, (byte) 0x3b, (byte) 0x85,
            (byte) 0x37, (byte) 0x3f, (byte) 0xeb, (byte) 0x99, (byte) 0x8d, (byte) 0x3d,
            (byte) 0x39, (byte) 0xaf};
        for (int j = 0; j < data.length; j++) {
            int a = 0;
            boolean ketemu = false;
            for (int i = 0; i < 256; i++) {
                //      ((byte)(~(*(char *)(param_1 + (int)local_10) << 1) ^ 0xaaU) == password[(int)local_10])
                if ((byte) (~((i + j) << 1) ^ (int) 0xaa) == data[j]) {
                    System.out.printf("%c", i + j);
                    break;
                }
            }
        }
    }

}
