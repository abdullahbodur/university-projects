import sys
x = int(sys.argv[1])
y = int(sys.argv[2])
z = x**y 
output = "{0}^{1} = {2}".format(x,y,z)
while z > 9: 
    output += " = "+" + ".join(str(z))
    z = sum([int(n) for n in str(z)])
    output += " = {}".format(z)  
print("Output : "+output)