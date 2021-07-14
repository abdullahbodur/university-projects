import sys
S = sys.argv[1]

arr = S.split(",")

totalEven = 0
totalNumber = 0
evenString = ""

for x in arr: 
    x = float(x)
    if x > 0:
        totalNumber +=x
        if x%2 == 0:
            totalEven+=x
            evenString+= ",{}".format(int(x))


print('Even Numbers: "{}"'.format(evenString.strip(",")))    
print("Sum of Even Numbers: {}".format(int(totalEven)))
print("Even Number Rate: {0:.3f}".format(totalEven/totalNumber))

        


