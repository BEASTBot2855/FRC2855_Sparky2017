package org.usfirst.frc2855.Sparky.PixyI2C;
public class GlobalMembers
{
	public static BlockType g_blockType; // use this to remember the next object block type between function calls

	public static int getStart()
	{
	  short w;
	  short lastw;

	  lastw = (short)0xffff; // some inconsequential initial value

	  while (true)
	  {
		w = getWord();
		if (w == 0 && lastw == 0)
		{
		  return 0; // in I2C and SPI modes this means no data, so return immediately
		}
		else if (w == DefineConstants.PIXY_START_WORD && lastw == DefineConstants.PIXY_START_WORD)
		{
		  g_blockType = BlockType.NORMAL_BLOCK; // remember block type
		  return 1; // code found!
		}
		else if (w == DefineConstants.PIXY_START_WORD_CC && lastw == DefineConstants.PIXY_START_WORD)
		{
		  g_blockType = BlockType.CC_BLOCK; // found color code block
		  return 1;
		}
		else if (w == DefineConstants.PIXY_START_WORDX) // this is important, we might be juxtaposed
		{
		  PixyI2C.getByte(); // we're out of sync! (backwards)
		}
		lastw = w; // save
	  }
	}

//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//byte getByte(); // external, does the right things for your interface

	public static short getWord()
	{
	  // this routine assumes little endian 
	  short w;
	  byte c;
	  c = PixyI2C.getByte();
	  w = PixyI2C.getByte();
	  w <<= 8;
	  w |= c;
	  return w;
	}

	public static int Main()
	{
	  int i = 0;
	  int curr;
	  int prev = 0;

	  // look for two start codes back to back
	  while (true)
	  {
		curr = getStart();
		if (prev != 0 && curr != 0) // two start codes means start of new frame
		{
		  System.out.printf("%d", i++);
		}
		prev = curr;
	  }
	}

	public static int g_skipStart = 0;
	public static Block g_blocks;

	public static short getBlocks(short maxBlocks)
	{
	  byte i;
	  short w;
	  short blockCount;
	  short checksum;
	  short sum;
	  Block block;

	  if (g_skipStart == 0)
	  {
		if (getStart() == 0)
		{
		  return 0;
		}
	  }
	  else
	  {
		g_skipStart = 0;
	  }

	  for (blockCount = 0; blockCount < maxBlocks && blockCount < DefineConstants.PIXY_ARRAYSIZE;)
	  {
		checksum = getWord();
		if (checksum == DefineConstants.PIXY_START_WORD) // we've reached the beginning of the next frame
		{
		  g_skipStart = 1;
		  g_blockType = BlockType.NORMAL_BLOCK;
		  return blockCount;
		}
		else if (checksum == DefineConstants.PIXY_START_WORD_CC)
		{
		  g_skipStart = 1;
		  g_blockType = BlockType.CC_BLOCK;
		  return blockCount;
		}
		else if (checksum == 0)
		{
		  return blockCount;
		}

		block = g_blocks + blockCount;

//C++ TO JAVA CONVERTER WARNING: This 'sizeof' ratio was replaced with a direct reference to the array length:
//ORIGINAL LINE: for (i=0, sum=0; i<sizeof(Block)/sizeof(ushort); i++)
		for (i = 0, sum = 0; i < Block.length; i++)
		{
		  if (g_blockType == BlockType.NORMAL_BLOCK && i >= 5) // no angle for normal block
		  {
			block.angle = 0;
			break;
		  }
		  w = getWord();
		  sum += w;
		  w = (short) (block + i);
		}

		// check checksum
		if (checksum == sum)
		{
		  blockCount++;
		}
		else
		{
		  System.out.print("checksum error!\n");
		}

		w = getWord();
		if (w == DefineConstants.PIXY_START_WORD)
		{
		  g_blockType = BlockType.NORMAL_BLOCK;
		}
		else if (w == DefineConstants.PIXY_START_WORD_CC)
		{
		  g_blockType = BlockType.CC_BLOCK;
		}
		else
		{
		  return blockCount;
		}
	  }
	}

	public static void init()
	{
//C++ TO JAVA CONVERTER TODO TASK: The memory management function 'malloc' has no equivalent in Java:
//C++ TO JAVA CONVERTER TODO TASK: There is no Java equivalent to 'sizeof':
	  //g_blocks = (Block)(sizeof(Block) * DefineConstants.PIXY_ARRAYSIZE);
	}


//C++ TO JAVA CONVERTER TODO TASK: The implementation of the following method could not be found:
	//int sendByte(byte @byte);

	public static int send(byte[] data, int len)
	{
	  int i;
	  for (i = 0; i < len; i++)
	  {
		//sendByte(data[i]);
	  }

	  return len;
	}

/*	public static int setServos(short s0, short s1)
	{
	  byte[] outBuf = new byte[6];

	  outBuf[0] = 0x00;
	  outBuf[1] = (byte) DefineConstants.PIXY_SERVO_SYNC;
	  s0 = ((short)(outBuf + 2));
	  s1 = ((short)(outBuf + 4));

	  return send(outBuf, 6);
	}
*/
	public static int setBrightness(byte brightness)
	{
	  byte[] outBuf = new byte[3];

	  outBuf[0] = 0x00;
	  outBuf[1] = (byte) DefineConstants.PIXY_CAM_BRIGHTNESS_SYNC;
	  outBuf[2] = brightness;

	  return send(outBuf, 3);
	}

	public static int setLED(byte r, byte g, byte b)
	{
	  byte[] outBuf = new byte[5];

	  outBuf[0] = 0x00;
	  outBuf[1] = (byte) DefineConstants.PIXY_LED_SYNC;
	  outBuf[2] = r;
	  outBuf[3] = g;
	  outBuf[4] = b;

	  return send(outBuf, 5);
	}
}