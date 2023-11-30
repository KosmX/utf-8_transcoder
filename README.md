# utf-8_transcoder
## Convert windows local encodings to UTF-8, but leave UTF-8 files

---

I'm just trying to solve the most annoying issue of Windows OS:    
Random local encodings.  

This is a java program, what converts local-encoded files into UTF-8. But not touch anything else.  

`java -jar transcode.jar ".+\.((txt)|h|c|(cpp))" "windows-1250" "dir/to/recode/recursively"`

Usage:
1. copy `transcode` into a folder, in your path.
2. Edit the `FROM_ENCODING="windows-1250"` line, replace your source encoding (This is the default for Hungary and Central Europe)
3. `gradle build`, then copy `build/libs/transcoder-***.jar` into the same folder, you copied `transcoder`
4. `chmod +x transcoder`

You might want to edit the regex to inclide other file extensions.  
The program doesn't recognise binary so you'll have to filter files using extension. (as any binary is a valid *windows-retard-encoding* encoded file)


## Windows
Because this is a Java program, you can run it on anywhere, but I don't know how-to make a proper wrapper script for windows.

Alternatively you can use [WSL](https://docs.microsoft.com/en-us/windows/wsl/) on Windows for this.
