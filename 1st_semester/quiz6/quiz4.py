import sys

inputFile = open(sys.argv[1],"r")

messages = {}

# read inputFile.txt and added dictionary
for line in inputFile:
    messageLine = line.strip("\n").split()
    # if already exists an RoomID we only add new item to this dic else : we create new RoomId and add first item
    if messageLine[0] in messages: messages[messageLine[0]][messageLine[1]] = " ".join(messageLine[2:])
    else : messages[messageLine[0]] = {messageLine[1] : " ".join(messageLine[2:])} 

inputFile.close()

# change order of room messages
for chat in messages:
    keys = sorted(messages[chat]) 
    messages[chat] = {key : messages[chat][key] for key in keys}

# change order of rooms
keys = sorted(messages)
messages = {key : messages[key] for key in keys}

# write last state to outputFile.txt
outputFile = open(sys.argv[2],"w")
for chat in messages:
    outputFile.write("Message "+str(list(messages.keys()).index(chat)+1)+"\n")
    for mid in messages[chat]:
        outputFile.write(chat+" "+mid+" "+messages[chat][mid]+"\n")

outputFile.close()