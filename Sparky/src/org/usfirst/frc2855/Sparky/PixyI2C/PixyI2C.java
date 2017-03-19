package org.usfirst.frc2855.Sparky.PixyI2C;

public class PixyI2C {
  public final void init()
  {
	Wire.start();
  }
  public final void setArg(short arg)
  {
	if (arg == DefineConstants.PIXY_DEFAULT_ARGVAL)
	{
	  addr = Constants.PIXY_I2C_DEFAULT_ADDR;
	}
	else
	{
	  addr = arg;
	}
  }
  public final short getWord()
  {
	short w;
	byte c;
	Wire.requestFrom((int)addr, 2);
	c = Wire.readByte();
	w = Wire.read();
	w <<= 8;
	w |= c;
	return w;
  }
  public final static byte getByte()
  {
	Wire.requestFrom((int)addr, 1);
	return Wire.read();
  }

  public final byte send(tangible.RefObject<Byte> data, byte len)
  {
	Wire.beginTransmission(addr);
	Wire.write(data.argValue, len);
	Wire.endTransmission();
	return len;
  }

  private byte addr;
}

final class Constants
{
	public static final int PIXY_I2C_DEFAULT_ADDR = 0x54;
}
