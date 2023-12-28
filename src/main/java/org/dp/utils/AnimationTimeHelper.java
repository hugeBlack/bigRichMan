package org.dp.utils;

public class AnimationTimeHelper {
    private int durationMs;
    private double startTime = -1;

    private static UnitBezier unitBezier = new UnitBezier(0.5,0.0,0.5,1.0);

    public AnimationTimeHelper(int totalTimeMs){
         this.durationMs = totalTimeMs;
    }


    public void start(){
        startTime = Time.getTimeInMs();
    }

    public double getLinearProgress(){
        if(startTime == -1)
            throw new RuntimeException("Please call start() first.");

        double ans = (Time.getTimeInMs() - startTime) / durationMs;
        return ans > 1 ? 1.0 : ans;
    }

    // 默认配置成了首尾慢中间快
    public double getBezierProgress() {
        double linearProgress = getLinearProgress();
        if(linearProgress == 1)
            return 1.0;
        // https://github.com/WebKit/WebKit/blob/dcd42ae35333b577043cbe9e3eda1c4a5c66a471/Source/WebCore/platform/animation/TimingFunction.cpp#L115C29-L115C29
        double eps = 1.0 / durationMs;
        double ans = unitBezier.solve(getLinearProgress(), eps);
        if(ans < 0)
            return 0.0;
        else if(ans > 1)
            return 1.0;
        else
            return ans;
    }

}
