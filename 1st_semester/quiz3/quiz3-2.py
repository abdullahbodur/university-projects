import sys
def deleteNthElementsFromList(nth,lst): 
    del lst[nth-1::nth]
    return lst

numbers = [int(x) for x in sys.argv[1].split(",") if int(x) > 0]

if len(numbers) > 2:
    numbers = deleteNthElementsFromList(numbers[1],numbers)
    indx = 1

    while len(numbers) >= numbers[indx]:
        numbers = deleteNthElementsFromList(numbers[indx],numbers)
        indx += 1
        if len(numbers)-1 < indx : break

else : numbers = [1]        

print("Output :",*numbers)
