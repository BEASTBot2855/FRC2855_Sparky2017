package org.usfirst.frc2855.Sparky.I2CPixy;

final class DefineConstants
{
	public static final int PIXY_INITIAL_ARRAYSIZE = 30;
	public static final int PIXY_MAXIMUM_ARRAYSIZE = 130;
	public static final int PIXY_START_WORD = 0xaa55;
	public static final int PIXY_START_WORD_CC = 0xaa56;
	public static final int PIXY_START_WORDX = 0x55aa;
	public static final int PIXY_MAX_SIGNATURE = 7;
	public static final int PIXY_DEFAULT_ARGVAL = 0xffff;
	public static final int PIXY_MIN_X = 0;
	public static final int PIXY_MAX_X = 319;
	public static final int PIXY_MIN_Y = 0;
	public static final int PIXY_MAX_Y = 199;
	public static final int PIXY_RCS_MIN_POS = 0;
	public static final int PIXY_RCS_MAX_POS = 1000;
	public static final int PIXY_I2C_DEFAULT_ADDR = 0x54;
}