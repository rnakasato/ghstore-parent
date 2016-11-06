package com.nakasato.ghstore.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtils {
	public static void copyImage( String fileName, InputStream in ) throws IOException {
		File fileToSave =new File( SaveDirectory.IMG_DIR +fileName );
		// write the inputStream to a FileOutputStream
		OutputStream out =new FileOutputStream( fileToSave );

		int read =0;
		byte[] bytes =new byte[1024];

		while( ( read =in.read( bytes ) ) != -1 ) {
			out.write( bytes, 0, read );
		}

		in.close();
		out.flush();
		out.close();

		System.out.println( "New file created!" );
	}
}
