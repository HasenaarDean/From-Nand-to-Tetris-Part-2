@LCL // push LCL
D=M
@SP
A=M
M=D
@SP
M=M+1

@ARG // push ARG
D=M
@SP
A=M
M=D
@SP
M=M+1

@THIS // push THIS
D=M
@SP
A=M
M=D
@SP
M=M+1

@THAT // push THAT
D=M
@SP
A=M
M=D
@SP
M=M+1


@SP
D=M
@5
D=D-A
@VALUE //ARGS AMOUNT
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@FUNCTION
0;JMP
(RETURN_LABEL_LABEL_X)