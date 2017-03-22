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
// Edited version of the default code
// This program simply prints the detected object blocks 
// (including color codes) through the serial console.  It uses the Arduino's 
// ICSP port.  For more information go here:
//
// http://cmucam.org/projects/cmucam5/wiki/Hooking_up_Pixy_to_a_Microcontroller_(like_an_Arduino)
//
// It prints the detected blocks once per second because printing all of the 
// blocks for all 50 frames per second would overwhelm the Arduino's serial port.
// Also turns on LED at full
   
#include <Pixy.h>
#include <Adafruit_NeoPixel.h>

#define PIN 2

#define NUMPIXELS 18
Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

// This is the main Pixy object 
Pixy pixy;

void setup()
{

  pinMode(2, OUTPUT);
  
  pixy.init();
  pixy.setLED(255, 255, 255);

  pixels.begin();

  for (int i = 0; i < 18; i++) {
    pixels.setPixelColor(i, 0, 255, 0);
  }
  pixels.setPixelColor(1, 0, 255, 0);
  pixels.setPixelColor(2, 0, 255, 0);
  pixels.setPixelColor(3, 0, 255, 0);
  pixels.setPixelColor(4, 0, 255, 0);
  pixels.setPixelColor(5, 0, 255, 0);
  pixels.setPixelColor(6, 0, 255, 0);
  pixels.setPixelColor(7, 0, 255, 0);
  pixels.setPixelColor(8, 0, 255, 0);
  pixels.setPixelColor(9, 0, 255, 0);
  pixels.show();

  pinMode(11, OUTPUT);  
  pinMode(12, OUTPUT);
}

void loop()
{ 

  static int i = 0;
  int j;
  uint16_t blocks;
  int x1;
  int x2;
  long xavg;
  
  // grab blocks!
  
long aa;
int aaa;
long bb;
int bbb;
long cc;
int ccc;
long dd;  
int ddd;
  /*for (int q = 3; q > 0; q--) {
      for (int j = 0; j <= NUMPIXELS; j = j + 3) {
        pixels.setPixelColor(j + (-(q - 2)), 0, 255, 0);  //turn every third pixel on
      }
     
      pixels.show();

      delay(150);

    for (int j = 0; j <= NUMPIXELS; j = j + 3) {
        pixels.setPixelColor(j + (-(q - 2)), 0);      //turn every third pixel off
      }
  }  */

    /*for (int a = 0; a <= NUMPIXELS; a++) {   //set to black
      pixels.setPixelColor(a, 0);
    }  

    delay(150);
    for (int a = 0; a < 8; a++) {   //set random pixels to color
      aa = random(0, NUMPIXELS);
      aaa = (int) aa;
      bb = random(0, 150);
      bbb = (int) bb;
      cc = random(0, 150);
      ccc = (int) cc;  
      dd = random(0, 150);
      ddd = (int) dd;
      pixels.setPixelColor(aaa, bbb, ccc, ddd);
    }*/  

    // pixels.show();  

  blocks = pixy.getBlocks();
  // If there are detected blocks, print them!
  if (blocks) {
    i++;
          
    if (i%20==0) {
      x1 = pixy.blocks[1].x;  
      x2 = pixy.blocks[2].x;
      xavg = abs((x1 + x2) /2);
      if (xavg < 150) { // move right
        digitalWrite(11, HIGH); // Right
        digitalWrite(12, LOW);
      } else if (xavg > 170) { // move left
        digitalWrite(11, HIGH); // Left
        digitalWrite(12, LOW);
      } else { // on target
        digitalWrite(11, LOW); //go straight
        digitalWrite(12, LOW);
      }  
    }
  }  
}
