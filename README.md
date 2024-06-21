## What is IOPR
iopr is a file fragmentator, this is a personal project
there is for fragment file in a chunks for a then remake it
## How use
```
java -jar iopr.jar fragment -f <your file to fragment> -o <output path>
```
fragment file in chunks of 6 bytes, use `` -b <num of bytes> `` to select size of chuncks
```
java -jar iopr.jar remake -p <path of the output> -o <output file>
```
### Example
```
java -jar iopr.jar fragment -f executable.exe -o dist -b 12
```
make it with
```
java -jar iopr.jar remake -p dist -o executable.out.exe
```
