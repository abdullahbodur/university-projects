def printTable(mapList,point,text):
    """
    This function only prints the output of the given input to the terminal.
    Output has two types of states. Initial table and Final Table. This function can print both statements
    """
    print(text)
    for ri in mapList:
        print(*ri) 
    if point is not None : print("Your score: "+str(point))    

def stringtoList(strList,isTable):
    """
    Input is going to be string type. This function converts string to array
    """
    arr = []
    subArr = []
    wrList = ["L","R","U","D","X","*","A","M","W","C","P"]
    for cr in strList:
        if cr in wrList:
            subArr.append(cr)
        elif cr == "]" and len(subArr) >= 1:
            arr.append(subArr.copy())
            subArr.clear()
    return arr

def moveRabbit(nextDr,mapList,position):
    """
    This function only process on table for current position. After that returns new table and position variable
    """
    mapList[position[0]][position[1]] = "X"
    mapList[position[0]+nextDr[0]][position[1]+nextDr[1]] = "*"
    position= [position[0]+nextDr[0],position[1]+nextDr[1]]

    return [mapList,position]
 
def controlPosition(position,nextdr,point,mapList):
    """
    This function controls the next steps and changes point.
    """
    nextP = mapList[position[0]+nextdr[0]][position[1]+nextdr[1]]

    if nextP == "C":point += 10
    elif nextP == "P":return moveRabbit(nextdr,mapList,position) + [point,"P"]        
    elif nextP == "M": point -= 5
    elif nextP == "W":return [mapList,position,point,"W"]
    elif nextP == "A": point += 5      

    return moveRabbit(nextdr,mapList,position) + [point,"O"]

mapList = stringtoList(input("Please enter feeding map as a list:\n"),True)

moveList = stringtoList(input("Please enter direction of movements as a list:\n"),False)[0]

position =[[y,x] for y in range(len(mapList)) for x in range(len(mapList[y])) if mapList[y][x] == "*"][0]

point = 0

skey = "A"

printTable(mapList,None,"Your board is :")
for mi in range(len(moveList)):
    if moveList[mi] == "U" and position[0] > 0: mapList,position,point,skey =  controlPosition(position,[-1,0],point,mapList)
    elif moveList[mi] == "D" and position[0] < len(mapList)-1: mapList,position,point,skey =  controlPosition(position,[1,0],point,mapList)
    elif moveList[mi] == "L" and position[1] > 0: mapList,position,point,skey =  controlPosition(position,[0,-1],point,mapList)
    elif moveList[mi] == "R" and position[1] < len(mapList[position[0]])-1: mapList,position,point,skey =  controlPosition(position,[0,1],point,mapList)                          

    if skey == "P": 
        printTable(mapList,point,"Your output should be like this :")
        break

    if mi == len(moveList)-1 and  skey != "P": printTable(mapList,point,"Your output should be like this :")
