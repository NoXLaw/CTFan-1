# Reversing: core
Given core dump of executable `./run` and the source code `run.c`.
First guess we need to find the flag from the memory, since the app crashed and core dump collected.
## Static Analysis
Analyzing the core:
```
core: ELF 64-bit LSB core file, x86-64, version 1 (SYSV), SVR4-style, from './run', real uid: 1000, effective uid: 1000, real gid: 1000, effective gid: 1000, execfn: './run', platform: 'x86_64'
```

Reading the `run.c` found interesting function:
```c
void xor(char *str, int len) {
	for (int i = 0; i < len; i++) {
		str[i] = str[i] ^ 1;
	}
}
```
## Solution
1. We can find the flag by searching the `core` binary data, since we already know the flag must be stored in the XORed by 1 in the memory
1. Spin some debugger to load the core dump file

I choose the first solution.

Since we already know the flag always be started by `TUCTF{` we can use this data as signature to search entire core dump file. Don't fortget to perform XOR to the signature data.

`54 55 43 54 46 7b` XORed by `01 01 01 01 01 01`

result = `55 54 42 55 47 7a`

Now we can find this data in core dump file:

```shell
Admin@sybond-MBP TUCTF % hexdump -C core | grep "55 54 42 55 47 7a"
00006040  55 54 42 55 47 7a 62 31  73 32 5e 65 74 6c 71 3e  |UTBUGzb1s2^etlq>|
Admin@sybond-MBP TUCTF % hexdump -C core | grep 000060             
00000060  1c 0e 00 00 00 00 00 00  00 00 00 00 00 00 00 00  |................|
00000600  fc 7f 00 00 00 00 00 00  00 00 00 00 ff ff ff ff  |................|
00006000  00 00 00 00 00 00 00 00  08 c0 a5 e3 25 56 00 00  |............%V..|
00006010  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  |................|
00006020  a0 06 2e 57 68 7f 00 00  00 00 00 00 00 00 00 00  |...Wh...........|
00006030  80 f9 2d 57 68 7f 00 00  00 00 00 00 00 00 00 00  |..-Wh...........|
00006040  55 54 42 55 47 7a 62 31  73 32 5e 65 74 6c 71 3e  |UTBUGzb1s2^etlq>|
00006050  5e 4f 32 77 32 73 5e 69  32 35 73 65 5e 31 67 5e  |^O2w2s^i25se^1g^|
00006060  78 31 74 7c 0b 00 00 00  00 00 00 00 00 00 00 00  |x1t|............|
00006070  00 00 00 00 00 00 00 00  00 00 00 00 00 00 00 00  |................|
Admin@sybond-MBP TUCTF %

```
Now we can see this data:
```
55 54 42 55 47 7a 62 31 73 32 5e 65 74 6c 71 3e 5e 4f 32 77 32 73 5e 69 32 35 73 65 5e 31 67 5e 78 31 74 7c
```
after XOR with `0x01` result `54 55 43 54 46 7b 63 30 72 33 5f 64 75 6d 70 3f 5f 4e 33 76 33 72 5f 68 33 34 72 64 5f 30 66 5f 79 30 75 7d`. Or in other presentation (text):
```
TUCTF{c0r3_dump?_N3v3r_h34rd_0f_y0u}
```
