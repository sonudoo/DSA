# Copyright Sushant Kumar Gupta, Birla Institute of Technology, Mesra
# This program solves a 14x14 grid. A bot is allowed to move on the positions marked 1
# The bot can either move left,right,up,down or diagonal. The bot starts from row 1 and any valid column position.
# Task: To find the shortest path from the source row (row 1) to last row (row 14)
# Concept used: Backtracking Algorithm
# Algorithm complexity: Worst case: O(2^n)
grid = [[1,1,0,0,0,0,0,0,0,0,0,0,0,0],	# Starting row, Note only Column 1 and 2 are valid starting positions
		[0,0,1,1,0,0,0,0,0,0,0,0,0,0],
		[0,0,0,0,1,1,0,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,0,1,1,0,0,0,0],
		[0,0,0,0,0,0,0,0,0,0,1,1,0,0],
		[0,0,0,0,0,0,0,0,0,0,0,0,1,1],
		[0,0,0,0,0,0,0,0,0,0,1,1,0,0],
		[0,0,0,0,0,0,0,0,1,1,0,0,0,0],
		[0,0,0,0,0,0,1,1,0,0,0,0,0,0],
		[0,0,0,0,1,1,0,0,0,0,0,0,0,0],
		[0,0,1,1,0,0,0,0,0,0,0,0,0,0],
		[1,1,0,0,0,0,0,0,0,0,0,0,0,0],
		[0,0,1,1,0,0,0,0,0,0,0,0,0,0]]  # Last row to be reached through the shortest path.

# The following code just prints out the grid to be solved to the screen
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
print("The given grid to be solved is : \n")
for i in range(0,14):
	row_data = ""
	for j in range(0,14):
		row_data = row_data + " " + str(grid[i][j])
	print(row_data)
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
# Grid printing code ends

ans = []	# Temporary list, used to store the current path through which the bot travels.
total = 0 	# Total number of paths found
finalpathlist = []	# Final list of lists of all possible paths. May not contain every path if the shortest path is found before hand itself
finallengthlist = []	# Final list of length of all possible path

# The following function writes the answer to the screen
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
def writeans():
	if(total > 0):	# If at least one path is found
		minlength = 15*15	# some large value
		minlengthindex = 0
		for i in range(0,len(finallengthlist)):
			if(finallengthlist[i]<minlength):
				minlength = finallengthlist[i]
				minlengthindex = i
		print("\nThe shortest path is : " + str(finalpathlist[minlengthindex]))
		print("\nThe length of the shortest path is : "+str(minlength))
	else:	# If no path is found
		print("\nNo Path found")
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 

#  The following is a major function which computes the shortest path. It is recursively called
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
#  i is the current row number, j is the current column number. Indices starts from 0
def solve(i,j,routelength):
	global total      # Use the global value of total variable
	if(i==13):		  # If we are on the last row, append the route data to finalpathlist and finallengthlist
		ll = []		  # Temporary list
		for i in ans:
			ll.append(i)	# Copy the route data to the temporary list
		finalpathlist.append(ll)	# Append the list
		finallengthlist.append(routelength)
		total = total + 1
		if(routelength==13):	# If the route length is 13, print the answer and exit as there can no path of length shorter than 13
			writeans()
			exit(0)
		return
	if(j-1>=0):		# If the bot can move diagonal left or left
		if(grid[i+1][j-1]==1):	# Bot moves to diagnal left
			pp = []		# Temporaray list to store next block data, and then append to ans
			pp.append(i+2)
			pp.append(j)
			ans.append(pp)
			solve(i+1,j-1,routelength+1)
			ans.pop()
		if(grid[i][j-1]==1):	# Bot moves to left
			pp = []		# Temporaray list to store next block data, and then append to ans
			pp.append(i+1)
			pp.append(j)
			if(pp not in ans):	# Make sure the bot doesn't go back to the position it has came from
				ans.append(pp)
				solve(i,j-1,routelength+1)
				ans.pop()
	if(j+1<=13):	# If the bot can move diagonal right or right
		if(grid[i+1][j+1]==1):		# Bot moves to diagonal right
			pp = []		# Temporaray list to store next block data, and then append to ans
			pp.append(i+2)
			pp.append(j+2)
			ans.append(pp)
			solve(i+1,j+1,routelength+1)
			ans.pop()
		if(grid[i][j+1]==1):	# Bot moves to right
			pp = []		# Temporaray list to store next block data, and then append to ans
			pp.append(i+1)
			pp.append(j+2)
			if(pp not in ans):	# Make sure the bot doesn't go back to the position it has came from
				ans.append(pp)
				solve(i,j+1,routelength+1)
				ans.pop()
	if(grid[i+1][j]==1):	# If the bot can move down
		pp = []		# Temporaray list to store next block data, and then append to ans
		pp.append(i+2)
		pp.append(j+1)
		ans.append(pp)
		solve(i+1,j,routelength+1)
		ans.pop()
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 

# For all possible starting positions, the backtracking starts from the for loop
for i in range(0,14):
	if(grid[0][i]==1):
		pp = []
		pp.append(1)
		pp.append(i+1)
		ans.append(pp)
		solve(0,i,0)
		ans.pop()

# Finally write the answer
writeans()