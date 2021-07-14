import sys

output = ""

try:

    # check parameter number whether equal 5 or not
    assert len(sys.argv) == 5, "Parameter number"

    opType, keyPath, inputFilePath, outputFilePath = sys.argv[1:5]

    # check extension of parameters
    assert ".txt" in keyPath, "KeyPath undefined extension"
    assert ".txt" in inputFilePath, "InputFilePath undefined extension"
    assert ".txt" in outputFilePath, "OutputFilePath undefined extension"

    characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "

    # check Operation Type of this program whether include enc, dec or not
    assert opType in ["enc", "dec"], "Undefined parameter"

    def invertMatrix(matrix):

        # This function convert given Matrix to Inverse Matrix.

        def generateIMatrix(N):

            # This function generate a Identity Matrix with given Number "N"

            tempList = []
            for ci in range(N):
                tempList.append([])
                for ri in range(N):
                    if ci == ri:
                        tempList[ci].append(1)
                    else:
                        tempList[ci].append(0)

            return tempList

        def scaleRow(matrixI):

            # This function multiple row with given arguments

            nonlocal matrix
            val = matrix[ni][ni]

            if float(val) == 0.0:

                cloneIndex = [ci for ci in range(N) if matrix[ci][ni] != 0][0]
                matrix[ni][ni:] = [
                    *[sum(x) for x in zip(matrix[ni][ni:], matrix[cloneIndex][ni:])]
                ]
                val = matrix[ni][ni]

                matrixI[ni] = [
                    *[sum(xi) for xi in zip(matrixI[ni], matrixI[cloneIndex])]
                ]

            for A_rowIndex in range(ni, N):
                matrix[ni][A_rowIndex] = matrix[ni][A_rowIndex] / val

            for I_rowIndex in range(N):
                matrixI[ni][I_rowIndex] = matrixI[ni][I_rowIndex] / val

        def subtractRow(processIndex, matrixI):

            # This function subtract column with given arguments

            nonlocal matrix

            s_subtractor = matrix[processIndex][ni]

            for A_rowIndex in range(ni, N):

                d_subtractor = matrix[ni][A_rowIndex]

                matrix[processIndex][A_rowIndex] -= s_subtractor * d_subtractor

            for I_rowIndex in range(N):

                d_subtractor = matrixI[ni][I_rowIndex]

                matrixI[processIndex][I_rowIndex] -= s_subtractor * d_subtractor

        N = len(matrix)

        matrixI = generateIMatrix(N)

        for ni in range(N):
            scaleRow(matrixI)

            if ni > 0:
                for topIndex in range(ni):
                    subtractRow(topIndex, matrixI)

            if ni < N - 1:
                for bottomIndex in range(ni + 1, N):
                    subtractRow(bottomIndex, matrixI)

        for ci in range(N):
            for ri in range(N):
                matrixI[ci][ri] = round(matrixI[ci][ri])

        return matrixI

    def textHasher(keyList, inputList):

        n = len(keyList[0])

        inputLength = len(inputList)

        recMatrix, resultList = [], []

        for i in range(0, inputLength, n):
            recMatrix.append([[li] for li in inputList[i : i + n]])
            resultList.append([[0] for li in range(n)])

        for g in range(len(recMatrix)):
            for i in range(n):
                for k in range(n):
                    resultList[g][i][0] += keyList[i][k] * recMatrix[g][k][0]

        return resultList

        # read key and convert this key to matrix n x n

    try:
        with open(keyPath, "r") as file:

            file = str(file.read())

            assert file != "", "Key file is empty"

            keyList = file.strip(" ").strip("\n").split("\n")
            keyList = [row.split(",") for row in keyList]

            for ri in range(len(keyList)):
                for ci in range(len(keyList)):
                    keyList[ri][ci] = int(keyList[ri][ci])

    except FileNotFoundError:
        raise FileNotFoundError("Key file not found")

    except IOError:
        raise IOError("Key file could not be read")

    except ValueError:
        raise ValueError("Invalid character in key file")

    try:

        with open(inputFilePath, "r") as file:
            inputFileContent = str(file.read()).strip("\n").strip(" ")

        assert inputFileContent != "", "Input file is empty"

    except FileNotFoundError:
        raise FileNotFoundError("Input file not found")

    except IOError:
        raise IOError("The input file could not be read")

    n = len(keyList[0])

    if opType == "dec":

        encryptedList = inputFileContent.split(",")

        encryptedList = [int(li) for li in encryptedList]
        encryptedLength = len(encryptedList)

        keyList = invertMatrix(keyList)

        decResult = textHasher(keyList, encryptedList)

        try:
            for g in range(len(decResult)):
                for i in range(len(decResult[g])):
                    output += characters[decResult[g][i][0] - 1]
        except IndexError:
            raise IndexError(
                "When given matrix is convert inverse, It contains decimal number"
            )

        with open(outputFilePath, "w") as file:
            file.write(output)

    elif opType == "enc":

        inputFileContent = inputFileContent.upper()

        letterList = [characters.index(letter) + 1 for letter in inputFileContent]

        countOfNeed = n - (len(letterList) % n)

        if 0 <= countOfNeed < n:
            letterList += [27] * countOfNeed

        encResult = textHasher(keyList, letterList)

        for g in range(len(encResult)):
            for i in range(len(encResult[g])):
                output += str(encResult[g][i][0]) + ","

        with open(outputFilePath, "w") as file:
            file.write(output.strip(","))

except (AssertionError, FileNotFoundError, IOError, ValueError, IndexError) as error:
    print(error)