package com.example.broadcast.util;

import com.example.broadcast.mudule.OnDeviceNotify;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface TriColorSdkUtil extends StdCallLibrary {
    TriColorSdkUtil INSTANCE = (TriColorSdkUtil) Native.load("TriColorSDK", TriColorSdkUtil.class);
    int GetVersion();

    /**
     * 1.初始化
     * @param sIP
     * @param uPort
     * @param uHeartBeatTimeout
     * @param sDeviceType
     * @return
     */
    int Init(String sIP, int uPort, int uHeartBeatTimeout, String sDeviceType);

    /**
     * 反初始化
     * @return
     */
    int DeInit();

    /**
     * 2.添加设备
     * @param sIp
     * @param uPort
     * @param fNotify
     * @param pUserParam
     * @return
     */
    int RegisterDevice(String sIp, int uPort, OnDeviceNotify fNotify, int pUserParam);

    /**
     * 3.删除设备
     * @param uDevId
     * @return
     */
    int UnregisterDevice(int uDevId);

    /**
     * 4.打开屏幕
     * @param uDevId
     * @return
     */
    int OpenDevice(int uDevId);

    /**
     * 5.关闭屏幕
     * @param uDevId
     * @return
     */
    int CloseDevice(int uDevId);

    /**
     * 6.设置亮度
     * @param uDevId
     * @param uLightnessValue
     * @return
     */
    int SetDeviceLightness(int uDevId, int uLightnessValue);

    /**
     * 7.LED显示文本
     * @param uDevId
     * @param uEncodeMode
     * @param uColor
     * @param pText
     * @param uField
     * @return
     */
    int ShowInstantText(int uDevId, int uEncodeMode, int uColor, StringBuilder pText, int uField);

    /**
     * 8.LED取消显示
     * @param uDevId
     * @param uField
     * @return
     */
    int HideInstantText(int uDevId, int uField);

    /**
     * 9.保存字符串内容到指定的索引
     * @param uDevId
     * @param uStringIndex
     * @param uEncodeMode
     * @param uColor
     * @param pText
     * @param uField
     * @return
     */
    int UpdateDeviceText(int uDevId, int uStringIndex, int uEncodeMode, int uColor, StringBuilder pText, int uField);

    /**
     * 10.切换内容显示
     * @param uDevId
     * @param uStringIndex
     * @param uField
     * @return
     */
    int SwitchDeviceText(int uDevId, int uStringIndex, int uField);

    /**
     * 11.删除保存在索引中的字符串内容
     * @param uDevId
     * @param uStringIndex
     * @param uField
     * @return
     */
    int DeleteDeviceText(int uDevId, int uStringIndex, int uField);
}
