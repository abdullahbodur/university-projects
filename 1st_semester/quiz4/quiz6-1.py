import sys

n = int(sys.argv[1])


def printDiamond(nl):
    def diamondRes(nl, dir):

        if nl == 1:
            return [(" " * (n - nl)) + "*" + (" " * (n - nl))]

        if dir == "r":
            return [
                (" " * (n - nl)) + ((2 * nl - 1) * "*") + (" " * (n - nl))
            ] + diamondRes(nl - 1, "r")

        else:
            return diamondRes(nl - 1, "l") + [
                (" " * (n - nl)) + ((2 * nl - 1) * "*") + (" " * (n - nl))
            ]

    if nl <= 1:
        return ["*"]

    return diamondRes(nl - 1, "l") + [((2 * nl - 1) * "*")] + diamondRes(nl - 1, "r")


l = printDiamond(n)

for line in l:
    print(line)
