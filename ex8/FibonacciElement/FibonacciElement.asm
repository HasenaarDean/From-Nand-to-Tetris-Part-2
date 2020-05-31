@256
D=A
@SP
M=D

@RETURN_LABEL_0
D=A
@SP
A=M
M=D
@SP
M=M+1
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
@0 //ARGS AMOUNT
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(RETURN_LABEL_0)
(Sys.init)
@4
D=A
@SP
A=M
M=D
@SP
M=M+1
@RETURN_LABEL_1
D=A
@SP
A=M
M=D
@SP
M=M+1
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
@1 //ARGS AMOUNT
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL_1)
(Sys.Main.fibonacci$WHILE)
@Sys.Main.fibonacci$WHILE
0;JMP
(Main.fibonacci)
@ARG
D=M
@0
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M

@y_1
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_1
M=D

@xandnoty_1
M=D
@y_1
D=M
D=!D
@xandnoty_1
M=D&M

@y_1
D=M
@notxandy_1
M=D
@x_1
D=M
D=!D
@notxandy_1
M=D&M
D=M

@xxory_1
M=D
@xandnoty_1
D=M
@xxory_1
M=D|M
D=M

@MANAGE_OVERFLOW_1
D;JLT

@y_1
D=M
@x_1
D=M-D
@TRUE_CONDITION_1
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_1
0;JMP

(TRUE_CONDITION_1)
D=-1
@SP
A=M
A=A-1
M=D
@END_1
0;JMP

(FALSE_CONDITION_1)
@END_1
0;JMP

(MANAGE_OVERFLOW_1)
@y_1
D=M
@Y_IS_THE_NEGATIVE_1
D;JLT
@TRUE_CONDITION_1
0;JMP

(Y_IS_THE_NEGATIVE_1)
D=0
@SP
A=M
A=A-1
M=D

(END_1)

@SP
M=M-1
A=M
D=M
A=A-1
@Main.Main.fibonacci$IF_TRUE
D;JNE
@Main.Main.fibonacci$IF_FALSE
0;JMP
(Main.Main.fibonacci$IF_TRUE)
@ARG
D=M
@0
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@R13
M=D 
@5
D=D-A
A=D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@R13
M=M-1
A=M
D=M
@THAT
M=D
@R13
M=M-1
A=M
D=M
@THIS
M=D
@R13
M=M-1
A=M
D=M
@ARG
M=D
@R13
M=M-1
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
(Main.Sys.init$IF_FALSE)
@ARG
D=M
@0
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M-D
@RETURN_LABEL_2
D=A
@SP
A=M
M=D
@SP
M=M+1
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
@1 //ARGS AMOUNT
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL_2)
@ARG
D=M
@0
D=D+A
A=D
D=M
@SP
A=M
M=D
@SP
M=M+1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M-D
@RETURN_LABEL_3
D=A
@SP
A=M
M=D
@SP
M=M+1
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
@1 //ARGS AMOUNT
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL_3)
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M-1
M=M+D
@LCL
D=M
@R13
M=D 
@5
D=D-A
A=D
D=M
@R14
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@R13
M=M-1
A=M
D=M
@THAT
M=D
@R13
M=M-1
A=M
D=M
@THIS
M=D
@R13
M=M-1
A=M
D=M
@ARG
M=D
@R13
M=M-1
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
