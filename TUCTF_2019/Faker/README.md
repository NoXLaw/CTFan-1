# Reverse: Faker
Given executable binary:
```
faker: ELF 64-bit LSB pie executable, x86-64, version 1 (SYSV), dynamically linked, interpreter /lib64/ld-linux-x86-64.so.2, BuildID[sha1]=4941fc6369eadbfdf9c93233b5f2f765e7f568d4, for GNU/Linux 3.2.0, not stripped
```

## Static analysis
Lets fire up [Ghidra](https://ghidra-sre.org/) and open this binary. And we can see there is `printFlag` function.

~~~C
void printFlag(char *param_1)
{
  char *__dest;
  size_t sVar1;
  int local_30;  
  __dest = (char *)malloc(0x40);
  memset(__dest,0,0x40);
  strcpy(__dest,param_1);
  sVar1 = strlen(__dest);
  local_30 = 0;
  while (local_30 < (int)sVar1) {
    __dest[local_30] = (char)((int)((((int)__dest[local_30] ^ 0xfU) - 0x1d) * 8) % 0x5f) + ' ';
    local_30 = local_30 + 1;
  }
  puts(__dest);
  return;
}
~~~

Also there are 4 functions calling the `printFlag`:
1. A
2. B
3. C
4. thisone

```c
printFlag("\\PJ\\fCaq(Lw|)$Tw$Tw@wb@ELwbY@hk");

printFlag("\\PJ\\fCTq00;waq|w)L0LwL$|)L0k");

printFlag("\\PJ\\fChqqZw|0;w2l|wELL(wYqqE$ahk");

printFlag("\\PJ\\fC|)L0LTw@Yt@;Twmq0Lw|qw@w2$a@0;w|)@awmLL|Tw|)LwZL2lhhL0k");
```

## Solution

Easiest solution create simple C app using above functions:

```c
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void printFlag(char *param_1)

{
  char *__dest;
  size_t sVar1;
  int local_30;

  __dest = (char *)malloc(0x40);
  memset(__dest,0,0x40);
  strcpy(__dest,param_1);
  sVar1 = strlen(__dest);
  local_30 = 0;
  while (local_30 < (int)sVar1) {
    __dest[(long)local_30] =
         (char)((int)((((int)__dest[(long)local_30] ^ 0xfU) - 0x1d) * 8) % 0x5f) + ' ';
    local_30 = local_30 + 1;
  }
  puts(__dest);
  return;
}

int main () {
  printFlag("\\PJ\\fChqqZw|0;w2l|wELL(wYqqE$ahk");
  printFlag("\\PJ\\fCaq(Lw|)$Tw$Tw@wb@ELwbY@hk");
  printFlag("\\PJ\\fCTq00;waq|w)L0LwL$|)L0k");
  printFlag("\\PJ\\fC|)L0LTw@Yt@;Twmq0Lw|qw@w2$a@0;w|)@awmLL|Tw|)LwZL2lhhL0k");
  return(0);
}
```

Compile using `gcc`, and run it. And here is the output:
```
TUCTF{600d_7ry_bu7_k33p_l00k1n6}
TUCTF{n0p3_7h15_15_4_f4k3_fl46}
TUCTF{50rry_n07_h3r3_317h3r}
TUCTF{7h3r35_4lw4y5_m0r3_70_4_b1n4ry_7h4n_m3375_7h3_d3bu663r}
```

Last one I think more make sense for the flag. :)
