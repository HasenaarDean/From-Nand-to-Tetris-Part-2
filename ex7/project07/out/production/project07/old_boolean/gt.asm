@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
D=M-D
@TRUE_CONDITION_NUMBER_X
D;JGT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_X
0;JMP

(TRUE_CONDITION_NUMBER_X)
D=-1
@SP
A=M
A=A-1
M=D

(FALSE_CONDITION_NUMBER_X)

