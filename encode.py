import random

char = ord(input("please input a letter(A-Z)，or input any other to leave:"))
while(char >= 65 and char <= 90):
    t = random.randint(39, 381)
    en = t * 26 + (char-65)
    print(en)
    char = ord(input("please input a letter(A-Z)，or input any other to leave:"))