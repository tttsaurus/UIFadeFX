package com.tttsaurus.uifadefx.fade;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class FadeContainer {

    private final StopWatch stopWatch = new StopWatch();
    private final SmoothDamp smoothDamp = new SmoothDamp(0f, 1f, 0.35f);

    private float deltaTime = 0f; // seconds
    private boolean initialized = false;
    private float progress = 0f;

    private Boolean lastSignal = null;

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getProgress() {
        return progress;
    }

    /**
     * @param signal Whether the UI element is being hovered
     * @return Whether has update
     */
    public boolean update(boolean signal) {
        if (!initialized) {
            stopWatch.start();
            initialized = true;
            return false;
        }

        long t = stopWatch.getTime(TimeUnit.MILLISECONDS);
        stopWatch.reset();
        stopWatch.start();

        deltaTime = t / 1000f;

        if (lastSignal == null || lastSignal != signal) {
            lastSignal = signal;
            if (signal) {
                smoothDamp.setFrom(0f);
                smoothDamp.setTo(1f);
            } else {
                smoothDamp.setFrom(1f);
                smoothDamp.setTo(0f);
            }
        }

        if (progress == smoothDamp.getTo()) {
            return false;
        }

        progress = smoothDamp.evaluate(deltaTime);

        return true;
    }
}
