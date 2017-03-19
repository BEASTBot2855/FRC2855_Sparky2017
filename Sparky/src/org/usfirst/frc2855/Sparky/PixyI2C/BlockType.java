package org.usfirst.frc2855.Sparky.PixyI2C;
public enum BlockType
{
  NORMAL_BLOCK,
  CC_BLOCK; // color code block

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static BlockType forValue(int value)
	{
		return values()[value];
	}
}