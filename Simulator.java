import java.nio.file.*;
import java.io.*;
import java.nio.*;
import java.util.*;

public class Simulator {

  public static void main(String[] args) {

  try {

  File file = new File("C:/Users/Akshay/Downloads/mystest.mac");
  byte[] arrayOfBytes = new byte[(int) file.length()];
  FileInputStream fis = new FileInputStream(file);
  fis.read(arrayOfBytes);
  fis.close();

  /*System.out.println("memory size is:"+arrayOfBytes.length);
  for(int i=0; i<arrayOfBytes.length; i++)
	System.out.println("first element of memory is:"+arrayOfBytes[i]);*/

  short[] memory = new short[arrayOfBytes.length/2];

  ByteBuffer.wrap(arrayOfBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(memory);
  /*System.out.println("memory size is:"+memory.length);
  for(int i=0; i<memory.length; i++)
	System.out.println("first element of memory is:"+memory[i]);*/



  short pc=0,sp=0,ac=0,ir;              // Creating registers

  while(true){
    ir = memory[pc];
    pc++;                               // Increment PC register by 1
    //System.out.printf("%hd\n", ir);
    int opCode,address;

    opCode = ir & 0XF000;               // Seperate opCode from instruction
    address = ir & 0X0FFF;              // Seperate address from instruction

    opCode = opCode >> 12;              // Shifting opCode to left
    //System.out.printf("%d  %d\n", opCode, address);
    switch(opCode){

      case 0X0000 :                     // Load instruction
        ac = memory[address];
        break;

      case 0X0001 :                     // Store instruction
        memory[address] = ac;
        break;

      case 0X0002 :                     // Add instruction
        ac += memory[address];
        break;

      case 0X0003 :                     // Sub instruction
        ac -= memory[address];
        break;

      case 0X0004 :                     // Push instruction
        if (sp == 0)
          sp = (short)(arrayOfBytes.length/2);
        memory[--sp] = ac;
        break;

      case 0X0005 :                     // Aloc instruction
        if(sp == 0)
          sp = (short)(arrayOfBytes.length/2);
        sp -= address;
        break;

      case 0X0006 :                     // Dloc instruction
        sp += address;
        if(sp >= (short)(arrayOfBytes.length/2))
          sp -= (short)(arrayOfBytes.length/2);
        break;

      case 0X0007 :                     // Din instruction
        Scanner sc = new Scanner(System.in);
        ac = sc.nextShort();
        break;

      case 0x0008 :                     // Ldc instruction
        ac = (short)address;
        break;

      case 0X0009 :                     // Ja instruction
        pc = (short)address;
        break;

      case 0X000A :                     // Ret instruction
        pc = memory[sp++];
        break;

      case 0X000B :                     // Sout instruction
        while(memory[ac] != 0)
        System.out.printf("%c",memory[ac++]);
        break;

      case 0X000C :                     // Dout instruction
        System.out.printf("%d", ac);
        break;

      case 0X000D :                     // Jnz instruction
        if(ac != 0)
          pc = (short)address;
        break;

      case 0X000E :                     // Call instruction
        memory[--sp] = pc;
        pc = (short)address;
        break;

      case 0X000F :                     // Halt instruction
        System.exit(0);

    }
  }
    } catch (IOException e) {
              e.printStackTrace();
          }
}
}
