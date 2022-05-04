.globl classify

.text
# =====================================
# COMMAND LINE ARGUMENTS
# =====================================
# Args:
#   a0 (int)        argc
#   a1 (char**)     argv
#   a1[1] (char*)   pointer to the filepath string of m0
#   a1[2] (char*)   pointer to the filepath string of m1
#   a1[3] (char*)   pointer to the filepath string of input matrix
#   a1[4] (char*)   pointer to the filepath string of output file
#   a2 (int)        silent mode, if this is 1, you should not print
#                   anything. Otherwise, you should print the
#                   classification and a newline.
# Returns:
#   a0 (int)        Classification
# Exceptions:
#   - If there are an incorrect number of command line args,
#     this function terminates the program with exit code 31
#   - If malloc fails, this function terminates the program with exit code 26
#
# Usage:
#   main.s <M0_PATH> <M1_PATH> <INPUT_PATH> <OUTPUT_PATH>
classify:
	addi t0, x0, 5
    bne a0, t0, arg_error
    
    addi sp, sp, -52
    sw ra, 0(sp)
    sw s0, 4(sp)
    sw s1, 8(sp)
    sw s2, 12(sp)
    sw s3, 16(sp)
    sw s4, 20(sp)
    sw s5, 24(sp)
    sw s6, 28(sp)
    sw s7, 32(sp)
    sw s8, 36(sp)
    sw s9, 40(sp)
    sw s10, 44(sp)
    sw s11, 48(sp)
    
    add s0, x0, a0
    add s1, x0, a1
    add s2, x0, a2
    
    # Read pretrained m0
    addi a0, x0, 8
    jal malloc

    beq a0, x0, malloc_error
    add s3, x0, a0 # s3 = row and col for m0

    lw a0, 4(s1)
    add a1, x0, s3
    addi a2, s3, 4
    jal read_matrix
    add s4, x0, a0	# m0 matrix
    
    # Read pretrained m1
    addi a0, x0, 8
    jal malloc

    beq a0, x0, malloc_error
    add s5, x0, a0 # s5 = row and col for m1

    lw a0, 8(s1)
    add a1, x0, s5
    addi a2, s5, 4
    jal read_matrix
    add s6, x0, a0	# m1 matrix

    # Read input matrix
    addi a0, x0, 8
    jal malloc

    beq a0, x0, malloc_error
    add s7, x0, a0 # s7 = row and col input matrix

    lw a0, 12(s1)
    add a1, x0, s7
    addi a2, s7, 4
    jal read_matrix
    add s8, x0, a0	# input matirx

    # Compute h = matmul(m0, input)
	lw t0, 0(s3)
    lw t1, 4(s7)
    mul t0, t0, t1
    slli a0, t0, 2
    jal malloc
    beq a0, x0, malloc_error
    add s9, x0, a0
    add a6, x0, a0
    add a0, x0, s4
    
    lw a1, 0(s3)
    lw a2, 4(s3)
	mv a3, s8
    lw a4, 0(s7)
    lw a5, 4(s7)

    addi sp, sp, -4
    sw a6, 0(sp)
    jal matmul
    lw a6, 0(sp)
    addi sp, sp 4    

    # Compute h = relu(h)
	add a0, x0, a6
	lw t0, 0(s3)
    lw t1, 4(s7)
	mul a1, t0, t1
	
    addi sp, sp, -4
	sw a6, 0(sp)
    jal relu
    lw a6, 0(sp)
	addi sp, sp, 4
	
    # Compute o = matmul(m1, h)
    add t0, x0, a6
	lw t1, 4(s7)
	lw t2, 0(s5)
	mul t1, t1, t2 
    slli a0, t1, 2 
    
    addi sp, sp, -4
    sw t0, 0(sp)
    jal malloc
    lw t0, 0(sp)
    addi sp, sp 4 

    beq a0, x0, malloc_error
    add s0, x0, a0 
    add a6, x0, a0 
    add a0, x0, s6



    lw a1, 0(s5)
	lw a2, 4(s5)
	add a3, x0, t0
	lw a4, 0(s3)
	lw a5, 4(s7)



    addi sp, sp, -16
    sw t1, 0(sp)
    sw a1, 4(sp)
    sw a5, 8(sp)
    sw a6, 12(sp)
    jal matmul  
    lw a6, 12(sp)
    lw a5, 8(sp)
    lw a1, 4(sp) 
    lw t1, 0(sp)
    addi sp, sp, 16
    
    # Write output matrix o
    lw a0, 16(s1)
    lw a2, 0(s5)
    lw a3, 4(s7)
    mv a1, a6

    addi sp, sp, -8
    
    sw a6, 4(sp)
    jal write_matrix
    lw a6, 4(sp)
    
    addi sp, sp, 8
    
    # Compute and return argmax(o)
    add a0, x0, a6
	lw t1, 4(s7)
	lw t2, 0(s5)
	mul t1, t1, t2
    mv a1, t1

    jal argmax
    mv s11, a0
    
    # If enabled, print argmax(o) and newline
    bne s2, x0, end
    add a0, x0, s11
    jal print_int
    li a0 '\n'
    jal print_char

    #free memory
	mv a0 s3
    jal free
    mv a0 s4
    jal free
    mv a0 s5
    jal free
    mv a0 s6
    jal free
    mv a0 s7
    jal free
    mv a0 s8
    jal free
    mv a0 s9
    jal free
    mv a0 s0
    jal free
    

end:
    lw ra, 0(sp)
    lw s0, 4(sp)
    lw s1, 8(sp)
    lw s2, 12(sp)
    lw s3, 16(sp)
    lw s4, 20(sp)
    lw s5, 24(sp)
    lw s6, 28(sp)
    lw s7, 32(sp)
    lw s8, 36(sp)
    lw s9, 40(sp)
    lw s10, 44(sp)
    lw s11, 48(sp)
    addi sp, sp, 52
    
    ret

malloc_error:
    li a0, 26
    jal exit 

arg_error:
    li a0, 31
    jal exit

