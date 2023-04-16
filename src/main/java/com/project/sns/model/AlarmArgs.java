package com.project.sns.model;

import lombok.Data;

@Data
public class AlarmArgs {
    // 알람을 발생시킨 사람
    private Integer fromUserId;
    private Integer targetId;
}
