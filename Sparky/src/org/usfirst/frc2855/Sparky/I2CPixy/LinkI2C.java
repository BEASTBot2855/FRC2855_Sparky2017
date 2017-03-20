package org.usfirst.frc2855.Sparky.I2CPixy;

import edu.wpi.first.wpilibj.I2C;

//
// begin license header
//
// This file is part of Pixy CMUcam5 or "Pixy" for short
//
// All Pixy source code is provided under the terms of the
// GNU General Public License v2 (http://www.gnu.org/licenses/gpl-2.0.html).
// Those wishing to use Pixy source code, software and/or
// technologies under different licensing terms should contact us at
// cmucam@cs.cmu.edu. Such licensing terms are available for
// all portions of the Pixy codebase presented here.
//
// end license header
//
// This file is for defining the link class for I2C communications.  
//
// Note, the PixyI2C class takes two optional arguments, the first being the I2C address 
// of the Pixy you want to talk to and the second being the port on the RoboRIO you want to use (Onboard or MXP).
// The default address and port are 0x54 and kOnboard respectively.
// So, for example, if you wished to talk to Pixy at I2C address 0x55 and using the MXP port, declare like this:
//
// PixyI2C *Pixy = new PixyI2C(0x55, I2C::Port::kMXP);
//

public class LinkI2C
{
	public static byte[] data;
  public LinkI2C(byte address)
  {
	  this(address, I2C.Port.kOnboard);
  }
  public LinkI2C()
  {
	  this(DefineConstants.PIXY_I2C_DEFAULT_ADDR, I2C.Port.kOnboard);
  }
  public LinkI2C(int address, I2C.Port port)
  {
	  LinkI2C.Wire = new I2C(port, address);

  } 
  public final static byte[] getData() {
	  data = new byte[64];
	  Wire.readOnly(data, 64);  // if this doesn't work, try read();
	  return data;
  }
  public final static int send(byte[] data, int len) {
	Wire.writeBulk(data);
	return len;
  }

  private static I2C Wire = new I2C(I2C.Port.kOnboard, 0x54);
} 