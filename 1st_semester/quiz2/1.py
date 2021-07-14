import sys


a = float(sys.argv[1])
b = float(sys.argv[2])
c = float(sys.argv[3])

delta = b**2-4*a*c   # b^2 -4ac

if delta < 0:
    print("There is no real solution")
else : 
    rootOne = (-b-delta**(1.0/2.0))/2.0*a
    rootTwo = (-b+delta**(1.0/2.0))/2.0*a

    if rootOne == rootTwo :
        print("There are one solution \nSolution : {}".format(rootOne))
    else :
        print("There are two solutions \nSolutions : {0} , {1}".format(rootOne,rootTwo))

    








