package com.example.fatkick.subsystem.storage;

import com.example.fatkick.subsystem.progress.DailyProgressReport;

public interface ProgressInterface {
    void onCallBack(DailyProgressReport dailyProgressReport);
}
