num = int(input("please input number，input 0 to leave:"))
while(num != 0):
    num = num % 26
    print(chr(num+65))
    num = int(input("please input number，input 0 to leave:"))