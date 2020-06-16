package com.zzh.netcontrol.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Time 2020/3/8
 * Author Zzh
 * Description
 */
public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        jobFinished(jobParameters, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
