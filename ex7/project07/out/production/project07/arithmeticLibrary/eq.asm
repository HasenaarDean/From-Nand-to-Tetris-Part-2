@SP
A=M
A=A-1
D=M

@y_NUMBER_X
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_X
M=D

@xandnoty_NUMBER_X
M=D
@y_NUMBER_X
D=M
D=!D
@xandnoty_NUMBER_X
M=D&M

@y_NUMBER_X
D=M
@notxandy_NUMBER_X
M=D
@x_NUMBER_X
D=M
D=!D
@notxandy_NUMBER_X
M=D&M
D=M

@xxory_NUMBER_X
M=D
@xandnoty_NUMBER_X
D=M
@xxory_NUMBER_X
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_X
D;JLT



@y_NUMBER_X
D=M
@x_NUMBER_X
D=M-D
@TRUE_CONDITION_NUMBER_X
D;JEQ
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
@END_NUMBER_X
0;JMP

(FALSE_CONDITION_NUMBER_X)
@END_NUMBER_X
0;JMP


(MANAGE_OVERFLOW_NUMBER_X)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_X)