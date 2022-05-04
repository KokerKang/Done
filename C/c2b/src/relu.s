.globl relu

.text
# ==============================================================================
# FUNCTION: Performs an inplace element-wise ReLU on an array of ints
# Arguments:
#   a0 (int*) is the pointer to the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   None
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# ==============================================================================

# relu (arr, index):
#	'if index < 1:
#		li a0 36
#		j exit
#	temp = 0;
#	while (temp < index):
#		'if (arr[temp] < 0):
#			arr[temp] = 0
#		temp += 1
relu:
	# Prologue
	li t0, 0 # set index 0
	li t3, 1
	blt a1, t3, exception

loop_start:
	bge t0, a1, loop_end 	# check index condition 
	lw t1, 0(a0)			# get first array value
	blt t1, x0, loop_continue # if array value is less than 0, then go to loop continue
	addi a0, a0, 4			# pointing next array index
	addi t0, t0, 1			# updating index value
	j loop_start






loop_continue:
	sw x0, 0(a0)			# set the array value 0
	addi a0, a0, 4			# pointing next array index
	addi t0, t0, 1			# updating index value
	j loop_start

loop_end:
	# Epilogue
	ret
exception:
	li a0 36
	j exit
