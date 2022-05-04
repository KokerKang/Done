.globl matmul

.text
# =======================================================
# FUNCTION: Matrix Multiplication of 2 integer matrices
#   d = matmul(m0, m1)
# Arguments:
#   a0 (int*)  is the pointer to the start of m0
#   a1 (int)   is the # of rows (height) of m0
#   a2 (int)   is the # of columns (width) of m0
#   a3 (int*)  is the pointer to the start of m1
#   a4 (int)   is the # of rows (height) of m1
#   a5 (int)   is the # of columns (width) of m1
#   a6 (int*)  is the pointer to the the start of d
# Returns:
#   None (void), sets d = matmul(m0, m1)
# Exceptions:
#   Make sure to check in top to bottom order!
#   - If the dimensions of m0 do not make sense,
#     this function terminates the program with exit code 38
#   - If the dimensions of m1 do not make sense,
#     this function terminates the program with exit code 38
#   - If the dimensions of m0 and m1 don't match,
#     this function terminates the program with exit code 38
# =======================================================
matmul:

	addi t0, x0, 1
	# Error checks
	blt a1, t0, exception
	blt a2, t0, exception
	blt a4, t0, exception
	blt a5, t0, exception
	bne a2, a4, exception

	# Prologue
	addi sp, sp, -36
	sw s0, 0(sp)
	sw s1, 4(sp)
	sw s2, 8(sp)
	sw s3, 12(sp)
	sw s4, 16(sp)
	sw s5, 20(sp)
	sw s6, 24(sp)
	sw s7, 28(sp)
	sw ra, 32(sp)

	add s0, a0, x0
	add s1, a1, x0
	add s2, a2, x0
	add s3, a3, x0
	add s4, a4, x0
	add s5, a5, x0
	add s6, a6, x0
	
	add s7, x0, x0


	
	add t0, a0, x0 # temp matrix for m0
	add t3, a3, x0 # temp matrix for m1
	
	
	addi t5, x0, 0 # set outer index

	# currently, t5 = 0

	add s7, x0, s6 # temp matrix

	# currently, t5 = 0

outer_loop_start:
	beq t5, s1, outer_loop_end
	add t6, x0, x0 # set inner index
	# currently, t5 = 0
	




inner_loop_start:
	# currently, t5 = 0
	beq t6, s5, inner_loop_end
	beq t5, s1, outer_loop_end
	# currently, t5 = 0
	addi t2, x0, 4 # array index adder
	mul t1, t5, s2
	mul t1, t1, t2
	add a0, t0, t1 # arr = arr + i
	# currently, t5 = 0
	


	mul t4, t6, t2
	add a1, t3, t4
	
	add a2, x0, s2	# number of element to use
	addi a3, x0, 1	# stride of m0
	add a4, x0, s5 # stride of m1

	addi sp,sp, -28
	sw t5, 0(sp)
	sw t0, 4(sp)
	sw t1, 8(sp)
	sw t2, 12(sp)
	sw t3, 16(sp)
	sw t4, 20(sp)
	sw t6, 24(sp)

	# currently, t5 = 0
	jal dot
	
	
	lw t5, 0(sp)
	lw t0, 4(sp)
	lw t1, 8(sp)
	lw t2, 12(sp)
	lw t3, 16(sp)
	lw t4, 20(sp)
	lw t6, 24(sp)
	addi sp,sp, 28


	# dot product argument
	#   a0 (int*) is the pointer to the start of arr0 	
	#   a1 (int*) is the pointer to the start of arr1	
	#   a2 (int)  is the number of elements to use		
	#   a3 (int)  is the stride of arr0					
	#   a4 (int)  is the stride of arr1					
	


 
	
	sw a0, 0(s7) # return value put into result matrix
	
	addi t6, t6, 1 # updating index
	addi s7, s7, 4
	j inner_loop_start

inner_loop_end:
	# currently, t5 = ?
	addi t5, t5, 1
	j outer_loop_start




outer_loop_end:


	# Epilogue
	
	lw s0, 0(sp)
	lw s1, 4(sp)
	lw s2, 8(sp)
	lw s3, 12(sp)
	lw s4, 16(sp)
	lw s5, 20(sp)
	lw s6, 24(sp)
	lw s7, 28(sp)
	lw ra, 32(sp)
	addi sp, sp, 36
	ret


exception:
	li a0 38
	j exit
