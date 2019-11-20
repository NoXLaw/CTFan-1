# An album full of secrets
## Analisa awal
Flag di simpan dalam file PNG dengan metode steganograph. Dicoba dianalisa dengan [zsteg](https://github.com/zed-0xff/zsteg) hasilnya nihil. Beberapa data diperoleh antara lain:
1. Extra data `VGhlIHBhc3N3b3JkIGlzOiBhdGFsbWF0YWw=`; pada file `rooney.png` (decode BASE64 hasilnya "The password is: atalmatal")
1. Text comment `no SecDrive`
1. Ukuran file tidak pada umumnya untuk file: _leonardo.png, dev.png_

## Analisa kedua
Kemungkinan bagian dari PNG berisi data zlib berisi data lain selain image, yang kemudian di encrypt dengan password dari hasil analisa awal.

- PNGtoZlib -- Untuk melakukan extract zlib parts dari file PNG
- decomp.py -- Untuk melakukan deflate zlib data

Sejauh ini **nihil**..

## Ternyata
Setelah baca dari writeup-nya [p4](https://github.com/p4-team/ctf/tree/master/2019-11-16-asis-finals/secrets) mereka menggunakan `albumfs`. Belum nyoba sendiri, cukup mangguk-mangguk aja. :D

`ASIS{...}`
