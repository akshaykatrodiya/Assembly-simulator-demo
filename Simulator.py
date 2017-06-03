from array import array
import os

path = r'D:/SUNY/Semester-1/Python/Programs/mystest.mac'

memory = array('h')
size = os.stat(path).st_size
#print('size of file is:',size)

memory.fromfile(open(path,'rb'),int(size/2))
#print('complete memory is:',memory)
ac = 0
pc = 0
sp = 0

for x in memory[:]:
	while(1):
		ir = memory[pc]
		pc += 1                               #Increment PC register by 1 
		#print('ir is:',ir)
		
		opCode = ir & 0XF000                #Seperate opCode from instruction 
		address = ir & 0X0FFF               #Seperate address from instruction 
		
		opCode = opCode >> 12               #Shifting opCode to left 
		#print('opcode is:',opCode)
		#print('address is:',address)
		if opCode == 0x000:                      #Load instruction 
			ac = memory[address]
			break
		
		elif opCode == 1:                      #Store instruction 
			memory[address] = ac
			break
		
		elif opCode == 2:                      #Add instruction 
			ac += memory[address]
			break
		
		elif opCode == 3:                      #Sub instruction
			#print(ac)
			#print(memory[address])
			ac = int(ac) - int(memory[address])
			break
		
		elif opCode == 4:                      #Push instruction 
			sp -= 1
			memory[sp] = ac
			break
		
		elif opCode == 5:                      #Aloc instruction 
			sp = sp - address
			break
		
		elif opCode == 6:                      #Dloc instruction 
			sp += address
			break
		
		elif opCode == 7:                      #Din instruction 
			ac = input()
			break
		
		elif opCode == 8:                      #Ldc instruction 
			ac = address
			break
		
		elif opCode == 9:                      #Ja instruction 
			pc = address
			break
		
		elif opCode == 10:                      #Ret instruction 
			pc = memory[sp]
			sp += 1
			break
		
		elif opCode == 11:                      #Sout instruction 
			while(memory[ac] != 0):
				print(chr(memory[ac]), end="")
				ac += 1
			break
		
		elif opCode == 12:                      #Dout instruction 
			print(ac)
			break
		
		elif opCode == 13:                      #Jnz instruction 
			if ac != 0:
				pc = address
			break
		
		elif opCode == 14:                      #Call instruction 
			sp -= 1
			memory[sp] = pc
			pc = address
			break
		
		elif opCode == 15:                      #Halt instruction 
			exit(0)
			
		else:
			break
