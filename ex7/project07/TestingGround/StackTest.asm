@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
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

@y_NUMBER_1
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_1
M=D

@xandnoty_NUMBER_1
M=D
@y_NUMBER_1
D=M
D=!D
@xandnoty_NUMBER_1
M=D&M

@y_NUMBER_1
D=M
@notxandy_NUMBER_1
M=D
@x_NUMBER_1
D=M
D=!D
@notxandy_NUMBER_1
M=D&M
D=M

@xxory_NUMBER_1
M=D
@xandnoty_NUMBER_1
D=M
@xxory_NUMBER_1
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_1
D;JLT



@y_NUMBER_1
D=M
@x_NUMBER_1
D=M-D
@TRUE_CONDITION_NUMBER_1
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_1
0;JMP

(TRUE_CONDITION_NUMBER_1)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_1
0;JMP

(FALSE_CONDITION_NUMBER_1)
@END_NUMBER_1
0;JMP


(MANAGE_OVERFLOW_NUMBER_1)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_1)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@16
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

@y_NUMBER_2
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_2
M=D

@xandnoty_NUMBER_2
M=D
@y_NUMBER_2
D=M
D=!D
@xandnoty_NUMBER_2
M=D&M

@y_NUMBER_2
D=M
@notxandy_NUMBER_2
M=D
@x_NUMBER_2
D=M
D=!D
@notxandy_NUMBER_2
M=D&M
D=M

@xxory_NUMBER_2
M=D
@xandnoty_NUMBER_2
D=M
@xxory_NUMBER_2
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_2
D;JLT



@y_NUMBER_2
D=M
@x_NUMBER_2
D=M-D
@TRUE_CONDITION_NUMBER_2
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_2
0;JMP

(TRUE_CONDITION_NUMBER_2)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_2
0;JMP

(FALSE_CONDITION_NUMBER_2)
@END_NUMBER_2
0;JMP


(MANAGE_OVERFLOW_NUMBER_2)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_2)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
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

@y_NUMBER_3
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_3
M=D

@xandnoty_NUMBER_3
M=D
@y_NUMBER_3
D=M
D=!D
@xandnoty_NUMBER_3
M=D&M

@y_NUMBER_3
D=M
@notxandy_NUMBER_3
M=D
@x_NUMBER_3
D=M
D=!D
@notxandy_NUMBER_3
M=D&M
D=M

@xxory_NUMBER_3
M=D
@xandnoty_NUMBER_3
D=M
@xxory_NUMBER_3
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_3
D;JLT



@y_NUMBER_3
D=M
@x_NUMBER_3
D=M-D
@TRUE_CONDITION_NUMBER_3
D;JEQ
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_3
0;JMP

(TRUE_CONDITION_NUMBER_3)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_3
0;JMP

(FALSE_CONDITION_NUMBER_3)
@END_NUMBER_3
0;JMP


(MANAGE_OVERFLOW_NUMBER_3)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_3)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
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

@y_NUMBER_4
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_4
M=D

@xandnoty_NUMBER_4
M=D
@y_NUMBER_4
D=M
D=!D
@xandnoty_NUMBER_4
M=D&M

@y_NUMBER_4
D=M
@notxandy_NUMBER_4
M=D
@x_NUMBER_4
D=M
D=!D
@notxandy_NUMBER_4
M=D&M
D=M

@xxory_NUMBER_4
M=D
@xandnoty_NUMBER_4
D=M
@xxory_NUMBER_4
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_4
D;JLT



@y_NUMBER_4
D=M
@x_NUMBER_4
D=M-D
@TRUE_CONDITION_NUMBER_4
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_4
0;JMP

(TRUE_CONDITION_NUMBER_4)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_4
0;JMP

(FALSE_CONDITION_NUMBER_4)
@END_NUMBER_4
0;JMP


(MANAGE_OVERFLOW_NUMBER_4)
@y_NUMBER_4
D=M
@Y_IS_THE_NEGATIVE_NUMBER_4
D;JLT
@TRUE_CONDITION_NUMBER_4
0;JMP

(Y_IS_THE_NEGATIVE_NUMBER_4)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_4)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@892
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

@y_NUMBER_5
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_5
M=D

@xandnoty_NUMBER_5
M=D
@y_NUMBER_5
D=M
D=!D
@xandnoty_NUMBER_5
M=D&M

@y_NUMBER_5
D=M
@notxandy_NUMBER_5
M=D
@x_NUMBER_5
D=M
D=!D
@notxandy_NUMBER_5
M=D&M
D=M

@xxory_NUMBER_5
M=D
@xandnoty_NUMBER_5
D=M
@xxory_NUMBER_5
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_5
D;JLT



@y_NUMBER_5
D=M
@x_NUMBER_5
D=M-D
@TRUE_CONDITION_NUMBER_5
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_5
0;JMP

(TRUE_CONDITION_NUMBER_5)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_5
0;JMP

(FALSE_CONDITION_NUMBER_5)
@END_NUMBER_5
0;JMP


(MANAGE_OVERFLOW_NUMBER_5)
@y_NUMBER_5
D=M
@Y_IS_THE_NEGATIVE_NUMBER_5
D;JLT
@TRUE_CONDITION_NUMBER_5
0;JMP

(Y_IS_THE_NEGATIVE_NUMBER_5)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_5)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
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

@y_NUMBER_6
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_6
M=D

@xandnoty_NUMBER_6
M=D
@y_NUMBER_6
D=M
D=!D
@xandnoty_NUMBER_6
M=D&M

@y_NUMBER_6
D=M
@notxandy_NUMBER_6
M=D
@x_NUMBER_6
D=M
D=!D
@notxandy_NUMBER_6
M=D&M
D=M

@xxory_NUMBER_6
M=D
@xandnoty_NUMBER_6
D=M
@xxory_NUMBER_6
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_6
D;JLT



@y_NUMBER_6
D=M
@x_NUMBER_6
D=M-D
@TRUE_CONDITION_NUMBER_6
D;JLT
D=0

@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_6
0;JMP

(TRUE_CONDITION_NUMBER_6)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_6
0;JMP

(FALSE_CONDITION_NUMBER_6)
@END_NUMBER_6
0;JMP


(MANAGE_OVERFLOW_NUMBER_6)
@y_NUMBER_6
D=M
@Y_IS_THE_NEGATIVE_NUMBER_6
D;JLT
@TRUE_CONDITION_NUMBER_6
0;JMP

(Y_IS_THE_NEGATIVE_NUMBER_6)
D=0
@SP
A=M
A=A-1
M=D


(END_NUMBER_6)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
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

@y_NUMBER_7
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_7
M=D

@xandnoty_NUMBER_7
M=D
@y_NUMBER_7
D=M
D=!D
@xandnoty_NUMBER_7
M=D&M

@y_NUMBER_7
D=M
@notxandy_NUMBER_7
M=D
@x_NUMBER_7
D=M
D=!D
@notxandy_NUMBER_7
M=D&M
D=M

@xxory_NUMBER_7
M=D
@xandnoty_NUMBER_7
D=M
@xxory_NUMBER_7
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_7
D;JLT



@y_NUMBER_7
D=M
@x_NUMBER_7
D=M-D
@TRUE_CONDITION_NUMBER_7
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_7
0;JMP

(TRUE_CONDITION_NUMBER_7)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_7
0;JMP

(FALSE_CONDITION_NUMBER_7)
@END_NUMBER_7
0;JMP


(MANAGE_OVERFLOW_NUMBER_7)
@x_NUMBER_7
D=M
@7_IS_THE_NEGATIVE_NUMBER_7
D;JLT
@TRUE_CONDITION_NUMBER_7
0;JMP

(7_IS_THE_NEGATIVE_NUMBER_7)
D=0
@SP
A=M
A=A-1
M=D

(END_NUMBER_7)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32767
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

@y_NUMBER_8
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_8
M=D

@xandnoty_NUMBER_8
M=D
@y_NUMBER_8
D=M
D=!D
@xandnoty_NUMBER_8
M=D&M

@y_NUMBER_8
D=M
@notxandy_NUMBER_8
M=D
@x_NUMBER_8
D=M
D=!D
@notxandy_NUMBER_8
M=D&M
D=M

@xxory_NUMBER_8
M=D
@xandnoty_NUMBER_8
D=M
@xxory_NUMBER_8
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_8
D;JLT



@y_NUMBER_8
D=M
@x_NUMBER_8
D=M-D
@TRUE_CONDITION_NUMBER_8
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_8
0;JMP

(TRUE_CONDITION_NUMBER_8)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_8
0;JMP

(FALSE_CONDITION_NUMBER_8)
@END_NUMBER_8
0;JMP


(MANAGE_OVERFLOW_NUMBER_8)
@x_NUMBER_8
D=M
@8_IS_THE_NEGATIVE_NUMBER_8
D;JLT
@TRUE_CONDITION_NUMBER_8
0;JMP

(8_IS_THE_NEGATIVE_NUMBER_8)
D=0
@SP
A=M
A=A-1
M=D

(END_NUMBER_8)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
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

@y_NUMBER_9
M=D

@SP
M=M-1
@SP
A=M-1
D=M

@x_NUMBER_9
M=D

@xandnoty_NUMBER_9
M=D
@y_NUMBER_9
D=M
D=!D
@xandnoty_NUMBER_9
M=D&M

@y_NUMBER_9
D=M
@notxandy_NUMBER_9
M=D
@x_NUMBER_9
D=M
D=!D
@notxandy_NUMBER_9
M=D&M
D=M

@xxory_NUMBER_9
M=D
@xandnoty_NUMBER_9
D=M
@xxory_NUMBER_9
M=D|M
D=M

@MANAGE_OVERFLOW_NUMBER_9
D;JLT



@y_NUMBER_9
D=M
@x_NUMBER_9
D=M-D
@TRUE_CONDITION_NUMBER_9
D;JGT
D=0
@SP
A=M
A=A-1
M=D

@FALSE_CONDITION_NUMBER_9
0;JMP

(TRUE_CONDITION_NUMBER_9)
D=-1
@SP
A=M
A=A-1
M=D
@END_NUMBER_9
0;JMP

(FALSE_CONDITION_NUMBER_9)
@END_NUMBER_9
0;JMP


(MANAGE_OVERFLOW_NUMBER_9)
@x_NUMBER_9
D=M
@9_IS_THE_NEGATIVE_NUMBER_9
D;JLT
@TRUE_CONDITION_NUMBER_9
0;JMP

(9_IS_THE_NEGATIVE_NUMBER_9)
D=0
@SP
A=M
A=A-1
M=D

(END_NUMBER_9)
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
@53
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
M=M+D
@112
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
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M
M=-D
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
M=M&D
@82
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
M=M|D
@SP
A=M
A=A-1
D=M
@SP
M=M-1
@SP
A=M
M=!D
@SP
M=M+1
