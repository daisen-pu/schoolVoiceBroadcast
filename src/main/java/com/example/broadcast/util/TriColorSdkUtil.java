package com.example.broadcast.util;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface TriColorSdkUtil extends StdCallLibrary {
    TriColorSdkUtil INSTANCE = (TriColorSdkUtil) Native.load("TriColorSDK", TriColorSdkUtil.class);
    int GetVersion();
}
