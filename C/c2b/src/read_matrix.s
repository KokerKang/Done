.globl read_matrix

.text
# ==============================================================================
# FUNCTION: Allocates memory and reads in a binary file as a matrix of integers
#
# FILE FORMAT:
#   The first 8 bytes are two 4 byte ints representing the # of rows and columns
#   in the matrix. Every 4 bytes afterwards is an element of the matrix in
#   row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is a pointer to an integer, we will set it to the number of rows
#   a2 (int*)  is a pointer to an integer, we will set it to the number of columns
# Returns:
#   a0 (int*)  is the pointer to the matrix in memory
# Exceptions:
#   - If malloc returns an error,
#     this function terminates the program with error code 26
#   - If you receive an fopen error or eof,
#     this function terminates the program with error code 27
#   - If you receive an fclose error or eof,
#     this function terminates the program with error code 28
#   - If you receive an fread error or eof,
#     this function terminates the program with error code 29
# ==============================================================================
read_matrix:
    addi sp, sp, -32
    sw s0 0(sp)
    sw s1 4(sp)
    sw s2 8(sp)
    sw s3 12(sp)
    sw s4 16(sp)
    sw s5 20(sp)
    sw s6 24(sp)
    sw ra 28(sp)
    
    
    add s0, x0, a0
    add s1, x0, a1
    add s2, x0, a2

    add a0, x0, a0  
    addi a1, x0, 0    
    jal ra, fopen
    addi t0, x0, -1
    beq a0, t0, fopenerror89

   
    add s3, x0, a0
    add a0, x0, s3
    add a1, x0, s1
    addi a2, x0, 4
    jal ra, fread
    addi t0, x0, 4
    bne a0, t0, freaderror91

    # setup for col
    add a0, x0, s3
    add a1, x0, s2
    addi a2, x0, 4    
    jal ra, fread
    addi t0, x0, 4

    
    bne a0, t0, freaderror91

    lw t0, 0(s1)
    # col
    lw t1, 0(s2)
    
    mul t2, t0, t1
    addi t3, x0, 4
    mul a0, t2, t3
    add s4, x0, a0
    jal ra, malloc
    
    beq a0, x0, mallocerror88
    
    add s5, x0, a0
    add a0, x0, s3
    add a1, x0, s5
    add a2, x0, s4
    
    jal ra, fread
    bne a0, s4, freaderror91

    add a0, s3, x0
    jal ra, fclose

    bne a0, x0, fcloseerror90
    add a0, x0, s5


    # Epilogue
    lw s0 0(sp)
    lw s1 4(sp)
    lw s2 8(sp)
    lw s3 12(sp)
    lw s4 16(sp)
    lw s5 20(sp)
    lw s6 24(sp)
    lw ra 28(sp)
    addi sp, sp, 32

    ret

freaderror91:
    addi a0, x0, 29
    jal exit
fcloseerror90:
    addi a0, x0, 28
    jal exit
mallocerror88:
    addi a0, x0, 26
    jal exit
fopenerror89:
    addi a0, x0, 27
    jal exit