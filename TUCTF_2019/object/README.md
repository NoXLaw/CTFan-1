# Reverse: object
Given only executable binary object `run.o`. Since i can't execute the binary in my system, so I guess it is kind of password entry flag game.

## Static Analysis
Following output when trying to identify `run.o` using `file` utility:
```
run.o: ELF 64-bit LSB relocatable, x86-64, version 1 (SYSV), not stripped
```

Let's find out more about this binary using Ghidra. I found 2 interesting parts:
1. Function called `checkPassword`
2. Block of data labeled `password`

Here is the `checkPassword` function:
```c
void checkPassword(long param_1,undefined8 param_2,undefined8 param_3,undefined8 param_4)
{
  byte *pbVar1;
  char cVar2;
  byte bVar3;
  char *pcVar4;
  int *piVar5;
  char cVar6;
  char unaff_BL;
  undefined7 unaff_00000019;
  uint local_10;
  int local_c;

  cVar6 = (char)((ulong)param_4 >> 8);
  pcVar4 = (char *)func_0x00201024(param_1);
  *pcVar4 = *pcVar4 + (char)pcVar4;
  *pcVar4 = *pcVar4 + (char)pcVar4;
  if (local_c == (int)pcVar4 + 0x15b) {
    local_10 = 0;
  }
  else {
    piVar5 = (int *)func_0x00201046("Close, but no flag");
    cVar2 = (char)piVar5;
    *(char *)piVar5 = *(char *)piVar5 + cVar2;
    *(char *)piVar5 = *(char *)piVar5 + cVar2;
    *piVar5 = *piVar5 + 0x45c70000;
    *(char *)piVar5 = *(char *)piVar5 + cVar2;
    *(char *)piVar5 = *(char *)piVar5 + cVar2;
  }
  while ((int)local_10 < passlen) {
    if ((byte)(~(*(char *)(param_1 + (int)local_10) << 1) ^ 0xaaU) == password[(int)local_10]) {
      local_10 = local_10 + 1;
    }
    else {
      pcVar4 = (char *)func_0x002010b2("Incorrect password\nError at character: %d\n",
                                       (ulong)local_10);
      bVar3 = (byte)pcVar4;
      *pcVar4 = *pcVar4 + bVar3;
      *pcVar4 = *pcVar4 + bVar3;
      unaff_BL = unaff_BL + cVar6;
      pbVar1 = (byte *)(CONCAT71(unaff_00000019,unaff_BL) + -0x74fe07bb);
      *pbVar1 = *pbVar1 & bVar3;
    }
  }
  pcVar4 = (char *)func_0x002010cc("Correct Password!");
  *pcVar4 = *pcVar4 + (char)pcVar4;
  *pcVar4 = *pcVar4 + (char)pcVar4;
  return;
}
```

And, here is the `password` data block:
```
//
// .rodata
// SHT_PROGBITS  [0x188 - 0x226]
// ram: 00100188-00100226
//
DAT_00100188                                    XREF[3]:     checkPassword:0010006b(R),
                                                             00100228(*),
                                                             _elfSectionHeaders::00000150(*)  
00100188 fd              ??         FDh
00100189 ff              ??         FFh
0010018a d3              ??         D3h
0010018b fd              ??         FDh
0010018c d9              ??         D9h
0010018d a3              ??         A3h
0010018e 93              ??         93h
0010018f 35              ??         35h    5
00100190 89              ??         89h
00100191 39              ??         39h    9
00100192 b1              ??         B1h
00100193 3d              ??         3Dh    =
00100194 3b              ??         3Bh    ;
00100195 bf              ??         BFh
00100196 8d              ??         8Dh
00100197 3d              ??         3Dh    =
00100198 3b              ??         3Bh    ;
00100199 37              ??         37h    7
0010019a 35              ??         35h    5
0010019b 89              ??         89h
0010019c 3f              ??         3Fh    ?
0010019d eb              ??         EBh
0010019e 35              ??         35h    5
0010019f 89              ??         89h
001001a0 eb              ??         EBh
001001a1 91              ??         91h
001001a2 b1              ??         B1h
001001a3 33              ??         33h    3
001001a4 3d              ??         3Dh    =
001001a5 83              ??         83h
001001a6 37              ??         37h    7
001001a7 89              ??         89h
001001a8 39              ??         39h    9
001001a9 eb              ??         EBh
001001aa 3b              ??         3Bh    ;
001001ab 85              ??         85h
001001ac 37              ??         37h    7
001001ad 3f              ??         3Fh    ?
001001ae eb              ??         EBh
001001af 99              ??         99h
001001b0 8d              ??         8Dh
001001b1 3d              ??         3Dh    =
001001b2 39              ??         39h    9
001001b3 af              ??         AFh
001001b4 00              ??         00h
```

So, this part of `checkPassword` function perform necessary check for each character input against encrypted `password` data.

```c
if ((byte)(~(*(char *)(param_1 + (int)local_10) << 1) ^ 0xaaU) == password[(int)local_10]) {
  local_10 = local_10 + 1;
}
```
## Solution
To reverse the function and get all the each character check true we can make simple application using the same checking algorithm. Since Java is more practical to me, so here is the solution in Java:
```java
    public static void main(String[] args) {
        byte[] data = new byte[]{
            (byte) 0xfd, (byte) 0xff, (byte) 0xd3, (byte) 0xfd, (byte) 0xd9, (byte) 0xa3,
            (byte) 0x93, (byte) 0x35, (byte) 0x89, (byte) 0x39, (byte) 0xb1, (byte) 0x3d,
            (byte) 0x3b, (byte) 0xbf, (byte) 0x8d, (byte) 0x3d, (byte) 0x3b, (byte) 0x37,
            (byte) 0x35, (byte) 0x89, (byte) 0x3f, (byte) 0xeb, (byte) 0x35, (byte) 0x89,
            (byte) 0xeb, (byte) 0x91, (byte) 0xb1, (byte) 0x33, (byte) 0x3d, (byte) 0x83,
            (byte) 0x37, (byte) 0x89, (byte) 0x39, (byte) 0xeb, (byte) 0x3b, (byte) 0x85,
            (byte) 0x37, (byte) 0x3f, (byte) 0xeb, (byte) 0x99, (byte) 0x8d, (byte) 0x3d,
            (byte) 0x39, (byte) 0xaf};
        for (int j = 0; j < data.length; j++) {
            int a = 0;
            boolean ketemu = false;
            for (int i = 0; i < 256; i++) {
                //      ((byte)(~(*(char *)(param_1 + (int)local_10) << 1) ^ 0xaaU) == password[(int)local_10])
                if ((byte) (~((i + j) << 1) ^ (int) 0xaa) == data[j]) {
                    System.out.printf("%c", i + j);
                    break;
                }
            }
        }
    }
```

Here is the output when Java program executed, and also the flag:
```
TUCTF{c0n6r47ul4710n5_0n_br34k1n6_7h15_fl46}
```
