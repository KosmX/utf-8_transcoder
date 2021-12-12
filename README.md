# utf-8_transcoder
## Convert windows local encodings to UTF-8, but leave UTF-8 files

---

I'm just trying to solve the most annoying issue of Windows OS:    
Random local encodings.  

This is a java program, what converts local-encoded files into UTF-8. But not touch anything else.  


Usage:
1. copy `transcode` into a folder, in your path.
2. Edit the `FROM_ENCODING="windows-1250"` line, replace your source encoding
3. `gradle build`, then copy `build/libs/transcoder-***.jar` into the same folder, you copied `transcoder`
4. `chmod +x transcoder`

I use [WSL](https://docs.microsoft.com/en-us/windows/wsl/) on Windows for this.
