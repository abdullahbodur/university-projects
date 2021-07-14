import sys

def printResult(compareList,resultList):
    print("My results :         ",*resultList,sep=" ")
    print("Results to compare:  ",*compareList,sep=" ")

    
def printInput(operrandList):
    print("Given input : {}".format(" ".join(operrandList[i])))


try:
    try:
        operands = sys.argv[1]
        comparision_data = sys.argv[2]

    except IndexError :
        print("IndexError: number of input files less than expected.")
        exit()

    operrandList,comparisionList = [],[]


    try:
        with open(operands,"r") as file:
            for line in file:
                operrandList.append(line.split())

    except IOError:
        print("IOError: cannot open {}".format(operands))
        exit() 


    try:
        with open(comparision_data,"r") as file:
            for line in file:
                comparisionList.append(line.split())

    except IOError:
        print("IOError: cannot open {}".format(comparision_data))
        exit() 




    for i in range(len(operrandList)):
        try:
            operrand = operrandList[i]
            
            div,nondiv,start,end = (float(operrand[n]) for n in range(4))

            

            results = []
            for n in range(int(start),int(end+1)):
                if n % int(div) == 0 and n % int(nondiv) != 0:
                    results.append(n)

            compares = [int(c) for c in comparisionList[i]]

            assert results == compares

            printResult(compares,results)
            print("Goool!!!")

        except ValueError:
            print("ValueError: only numeric input is accepted.”")
            printInput(operrandList)

        except IndexError:
            print("IndexError: number of operands less than expected.")
            printInput(operrandList)

        except ZeroDivisionError:
            print("ZeroDivisionError: You can’t divide by 0.")
            printInput(operrandList)

        except AssertionError:
            print("Assertion Error: results don’t match.")
            printResult(compares,results)
        finally:
            print("------------")    
finally:
    print("\n"+"˜ Game Over ˜")
    





