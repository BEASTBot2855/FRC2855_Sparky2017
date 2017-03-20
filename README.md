# FRC2855_2017

This is a repository of code used to run BEASTBot's 2016 robot Sparky. There may be additions and deletions as we test different mechanisms for our current robot(s).

##  Java Pixy Code
Pixy I2C code is located at FRC2855_Sparky2017/Sparky/src/org/usfirst/frc2855/Sparky/I2CPixy

Currently only supports regular non-CC blocks. Servo control has been commented out. The code currently only gets the first two blocks of each frame, which can be changed by increasing or decreasing getNumBlocks in TPixy.java. dataInSize in TPixy.java may need to be increased to accomodate for more blocks.
