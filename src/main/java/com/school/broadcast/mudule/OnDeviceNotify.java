package com.school.broadcast.mudule;

import lombok.Data;

@Data
public class OnDeviceNotify {
    int uDeviceId;
    int pNotifiedData;
    int uCommand;
    int pUserParam;
}
