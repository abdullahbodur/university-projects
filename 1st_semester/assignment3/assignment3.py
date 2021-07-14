import sys
chessBoard = []
alphabet = "abcdefgh"

def failedControl(movementList):
    return [des for des in movementList if chessBoard[int(des[1])][des[0]].lower() != "ki"] 

def destinitonControl(chessBoard, piece):
    y, x = piece
    selectedPiece = chessBoard[y][x][0]
    h_index = alphabet.index(x)
    l, r = list(reversed(alphabet[:h_index])),list(alphabet[-(7-h_index):])

    def horizontalControl(oneStep, isWhite):

        def horizontalLooper(rng):
            arr = []
            for square in rng:
                if chessBoard[y][square] == "  ": arr.append(square+str(y))                    
                else:
                    if chessBoard[y][square].islower() != isWhite : arr.append(square+str(y))
                    break
            return ([arr[0]] if oneStep else arr) if len(arr)>0 else []

        return horizontalLooper(l) + horizontalLooper(r)

    def verticalControl(oneStep, isWhite, positiveDir,negativeDir):
        verticalList = []

        def verticalLooper(rng):
            arr = []
            for i in rng:
                if chessBoard[i][x] == "  ":arr.append(x+str(i))
                else:
                    if chessBoard[i][x].islower() != isWhite: arr.append(x+str(i))
                    break

            return ([arr[0]] if oneStep else arr) if len(arr)>0 else []

        if positiveDir : verticalList+= verticalLooper(range(y+1,9))  
        if negativeDir : verticalList+= verticalLooper(range(y-1, 0, -1))
        return verticalList

    def diagonalControl(oneStep, isWhite, positiveDir, negativeDir):
        diagonalList = []

        def looper(rng, alphabetList):
            arr = []

            alphabetList = alphabetList.copy()
            for i in rng:
                if min(i, len(alphabetList)) > 0:
                    if chessBoard[i][alphabetList[0]] == "  " :
                        arr.append(alphabetList[0]+str(i))
                        alphabetList.pop(0)
                    else:
                        if chessBoard[i][alphabetList[0]].islower() == isWhite:break
                        arr.append(alphabetList[0]+str(i))
                        alphabetList.pop(0)
                      
            return ([arr[0]] if oneStep else arr) if len(arr)>0 else []

        if positiveDir :diagonalList += looper(range(y+1, 9), r) + looper(range(y+1, 9), l)
        if negativeDir : diagonalList += looper(range(y-1, 0, -1), l) + looper(range(y-1, 0, -1), r)
        return diagonalList
        
    def destinitionLcontrol(isWhite):
        arr = []
        desL = list([xi,yi] for xi in [1,-1] for yi in [2,-2]) + list([yi,xi] for xi in [1,-1] for yi in [2,-2])
        for des in desL:
            if 1<=h_index+1+des[0] <= 8 and 1<= y+des[1] <= 8:
                if chessBoard[y+des[1]][alphabet[h_index+des[0]]] == "  ": arr.append(alphabet[h_index+des[0]]+str(y+des[1]))
                else:
                    if chessBoard[y+des[1]][alphabet[h_index+des[0]]].islower() == isWhite: continue
                    arr.append(alphabet[h_index+des[0]]+str(y+des[1]))             
        return arr

    if selectedPiece.lower() == "p": return (verticalControl(True,True,True,False) if selectedPiece.islower() else verticalControl(True,False,False,True))
    if selectedPiece.lower() == "r": return (verticalControl(False,True,True,True)+horizontalControl(False,True) if selectedPiece.islower() else verticalControl(False,False,True,True)+horizontalControl(False,False))
    if selectedPiece.lower()  == "n": return (destinitionLcontrol(True)+diagonalControl(True,True,True,False) if selectedPiece.islower() else destinitionLcontrol(False)+diagonalControl(True,False,False,True))
    if selectedPiece.lower()  == "b": return (diagonalControl(False,True,True,False) if selectedPiece.islower() else diagonalControl(False,False,False,True))
    if selectedPiece.lower()  == "k": return (horizontalControl(True,True)+verticalControl(True,True,True,True)+diagonalControl(True,True,True,True) if selectedPiece.islower() else horizontalControl(True,False)+verticalControl(True,False,True,True)+diagonalControl(True,False,True,True))
    if selectedPiece.lower()  == "q": return (horizontalControl(False,True)+verticalControl(False,True,True,True)+diagonalControl(False,True,True,True) if selectedPiece.islower() else horizontalControl(False,False)+verticalControl(False,False,True,True)+diagonalControl(False,False,True,True))

def initializeGame(chessBoard):
    chessBoard = {8: {"a": "R1", "b": "N1", "c": "B1", "d": "QU", "e": "KI", "f": "B2", "g": "N2", "h": "R2", },
                  7: {"a": "P1", "b": "P2", "c": "P3", "d": "P4", "e": "P5", "f": "P6", "g": "P7", "h": "P8", },
                  6: {"a": "  ", "b": "  ", "c": "  ", "d": "  ", "e": "  ", "f": "  ", "g": "  ", "h": "  ", },
                  5: {"a": "  ", "b": "  ", "c": "  ", "d": "  ", "e": "  ", "f": "  ", "g": "  ", "h": "  ", },
                  4: {"a": "  ", "b": "  ", "c": "  ", "d": "  ", "e": "  ", "f": "  ", "g": "  ", "h": "  ", },
                  3: {"a": "  ", "b": "  ", "c": "  ", "d": "  ", "e": "  ", "f": "  ", "g": "  ", "h": "  ", },
                  2: {"a": "p1", "b": "p2", "c": "p3", "d": "p4", "e": "p5", "f": "p6", "g": "p7", "h": "p8", },
                  1: {"a": "r1", "b": "n1", "c": "b1", "d": "qu", "e": "ki", "f": "b2", "g": "n2", "h": "r2", }}
    return "OK", chessBoard

def pieceFinder(piece,chessBoard): return list([y,x]for x in alphabet for y in range(1,9) if chessBoard[y][x] == piece)[0]

def movePiece(chessBoard, piece, des):
    propMovemements = failedControl(destinitonControl(chessBoard,piece)) 
    if des in propMovemements:
        selectedPiece = chessBoard[piece[0]][piece[1]]
        chessBoard[piece[0]][piece[1]] = "  "
        chessBoard[int(des[1])][des[0]] = selectedPiece
        return "OK", chessBoard
    else:
        return "FAILED", chessBoard

def boardPrinter(table): 
    print("-----------------------")
    for row in table:print(*table[row].values())
    print("-----------------------")

f = open(sys.argv[1], "r")
commands = [line.split() for line in f.readlines()]
f.close()

chessBoard = initializeGame(chessBoard)[1]

for command in commands:
    state = ""
    print(*command)
    if command[0] == "initialize": 
        state,chessBoard = initializeGame(chessBoard)
        boardPrinter(chessBoard)
    if command[0] == "showmoves":
        desList = sorted(failedControl(destinitonControl(chessBoard,pieceFinder(command[1],chessBoard))))  
        if len(desList) : print(*desList)
        else : print("FAILED")
    if command[0] == "move": state,chessBoard = movePiece(chessBoard,pieceFinder(command[1],chessBoard),command[2])
    if command[0] == "print":boardPrinter(chessBoard)
    if command[0] == "exit":exit()
    if state != "": print(state) 
