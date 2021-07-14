import sys

n = int(sys.argv[1])


if n >= 2:

    upList = [
        (" " * (n - i)) + ((2 * i - 1) * "*") + (" " * (n - i)) for i in range(1, n)
    ]

    downList = [
        (" " * (n - i)) + ((2 * i - 1) * "*") + (" " * (n - i))
        for i in range(n - 1, 0, -1)
    ]

    l = upList + ["*" * (2 * n - 1)] + downList

    for line in l:
        print(line)

else:
    print("*")