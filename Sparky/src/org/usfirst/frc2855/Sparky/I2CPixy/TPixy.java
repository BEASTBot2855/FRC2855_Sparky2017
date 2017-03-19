package org.usfirst.frc2855.Sparky.I2CPixy;

public class TPixy<LinkType> implements java.io.Closeable {
  public TPixy(){ }
  public final void close(){ }
  
  public static short[] block;
  
  public final static short[][] getBlocks() {
	  short word1;
	  short word2;
	  short[][] frameBlocks = new short[2][8];
	  int blockNum = 0;
	  int n;
	  short[] b;
	  
	  for (n=0; n<64;) {
		  word1 = LinkI2C.getWord(n);
		  word2 = LinkI2C.getWord(n+2);
		  if (word1 == 0xaa55 && word2 == 0xaa55 && blockNum == 0) {
			  b = saveBlock(blockNum, n+2);
			  frameBlocks[0] = b;
			  blockNum++;
		  } else if (word1 == 0xaa55 && word2 == 0xaa55 && blockNum == 1) {
			  b = saveBlock(blockNum, n+2);
			  frameBlocks[1] = b;
			  blockNum++;
		  } else {
		  }
	  }
	return frameBlocks;
	  
  }
  
  public final static short[] saveBlock(int num, int loc) {
	  short val;
	  block = new short[8];
	  	block[0] = (short) num;
	  	for (int w = 1; w < 8; w++) {
	  		val = LinkI2C.getWord((loc + 2*w));
	  		block[w] = val;
	  	}
	  return block;
  }
 
  //public final byte SetServos(short s0, short s1)
  //{
	//byte[] outBuf = new byte[6];

	//outBuf[0] = 0x00;
	//outBuf[1] = (byte)0xff;
	//s0 = ((short)(outBuf + 2));
	//s1 = ((short)(outBuf + 4));

	//return link.send(outBuf, 6);
  //}
  public final byte SetBrightness(byte brightness) {
	byte[] outBuf = new byte[3];

	outBuf[0] = 0x00;
	outBuf[1] = (byte)0xfe;
	outBuf[2] = brightness;

	return (byte) LinkI2C.send(outBuf, 3);
  }
  public final byte SetLED(byte r, byte g, byte b) {
	byte[] outBuf = new byte[5];

	outBuf[0] = 0x00;
	outBuf[1] = (byte)0xfd;
	outBuf[2] = r;
	outBuf[3] = g;
	outBuf[4] = b;

	return (byte) LinkI2C.send(outBuf, 5);
  }
}