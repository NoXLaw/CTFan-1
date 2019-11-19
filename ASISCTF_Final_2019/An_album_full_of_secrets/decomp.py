# now deflate zlib data
# 
# Usage: python decomp.py [raw_zlib_file]
#
# Output will be "[raw_zlib_file].un"

import zlib, sys

str_object1 = open(sys.argv[1], 'rb').read()
str_object2 = zlib.decompress(str_object1)
f = open(sys.argv[1]+".un", 'wb')
f.write(str_object2)
f.close()
