/*
 * The MIT License
 *
 * Copyright 2019 Bondan Sumbodo <sybond@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package sybond.web.id;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 * @author @sybond
 */
public class PNGtoZlib {

    private static byte[] buf;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length == 0) {
            System.out.println("No arguments.");
            System.out.println("");
            System.out.println("Expected: App.jar (PNG_file)");
            System.out.println("");
            System.out.println("The app will produce '(PNG_file).z'.");
            System.exit(5);
        }

        RandomAccessFile f = new RandomAccessFile(new File(args[0]), "r");
        RandomAccessFile f2 = new RandomAccessFile(new File(args[0] + ".z"), "rw");
        buf = new byte[0x21];  // skip magic bytes + IHDR data
        f.read(buf);
        while (true) {
            int len = f.readInt();
            int sig = f.readInt();
            System.out.printf("sig=%d\n", sig);
            buf = new byte[len];
            f.read(buf);
            if (sig == 1229209940) {  // "IDAT"
                f2.write(buf);
            }
            int crc = f.readInt();
            if (len < 2) {
                break;
            }
        }
        f.close();
        f2.close();
    }

}
