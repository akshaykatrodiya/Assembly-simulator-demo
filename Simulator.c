#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
	char buf[120];
	strcpy(buf, argv[1]);
	strcat(buf, ".mac");
	
	FILE *P;
	P = fopen(buf, "rb");
	
	//Registers
	short ac = 0, pc = 0, sp = 0, ir;
	short opcode, address;
	
	//Main memory
	short mem[4096];
	
	fread(mem, 2, 4096, P);
	
	while(1){
		
		//1. Fetch
		ir = mem[pc];
		
		//get the opcode
		opcode = ir & 0XF000;
		opcode = opcode >> 12;
		
		//get the address
		address = ir & 0X0FFF;
		
		//2. Increment pc
		pc++;
		
		//3. Decode and 4. Execute
		switch(opcode)
		{
			//load instruction
			case 0X0000: ac = mem[address];
					break;
			
			//store instruction
			case 0X0001: mem[address] = ac;
					break;
			
			//add instruction
			case 0X0002: ac = ac + mem[address];
					break;
			
			//sub instruction
			case 0X0003: ac = ac - mem[address];
					break;
			
			//push instruction
			case 0X0004: mem[--sp] = ac;
					break;
			
			//aloc instruction
			case 0X0005: sp = sp - address;
					break;
			
			//dloc instruction
			case 0X0006: sp = sp + address;
					break;
			
			//din instruction
			case 0X0007: scanf("%hd",&ac);
					break;
			
			//ldc instruction
			case 0X0008: ac = address;
			
			//ja instruction
			case 0X0009: pc = address; break;
			
			//ret instruction
			case 0X000A: pc = mem[sp++]; break;
			
			//sout instruction
			case 0X000B: while(mem[ac] != 0)
						printf("%c",mem[ac++]);
					break;
			
			//dout instruction
			case 0X000C: printf("%d",ac);
					break;
			
			//jnz instruction
			case 0X000D: if(ac != 0)
					pc = address;
					break;
			
			//call instruction
			case 0X000E: mem[--sp] = pc;
					pc = address;
					break;
			
			//halt instruction
			case 0X000F: exit(0);
		}
	}
}
