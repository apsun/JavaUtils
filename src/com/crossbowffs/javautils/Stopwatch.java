package com.crossbowffs.javautils;

import java.util.concurrent.TimeUnit;

public class Stopwatch {
    private long _startTime;
    private long _stopTime;
    private long _pauseTime;
    private boolean _running;
    private boolean _paused;

    public Stopwatch start() {
        long curr = System.nanoTime();
        assertState(!_running);
        _startTime = curr;
        _running = true;
        return this;
    }

    public Stopwatch stop() {
        long curr = System.nanoTime();
        assertState(_running);
        _stopTime = curr;
        _running = false;
        return this;
    }

    public Stopwatch reset() {
        _startTime = 0;
        _stopTime = 0;
        _running = false;
        _paused = false;
        return this;
    }

    public Stopwatch restart() {
        long curr = System.nanoTime();
        assertState(_running);
        _startTime = curr;
        _paused = false;
        return this;
    }

    public Stopwatch pause() {
        long curr = System.nanoTime();
        assertState(_running && !_paused);
        _pauseTime = _stopTime = curr;
        _paused = true;
        return this;
    }

    public Stopwatch resume() {
        long curr = System.nanoTime();
        assertState(_running && _paused);
        _startTime += (curr - _pauseTime);
        _paused = false;
        return this;
    }

    public long elapsedNanos() {
        long curr = System.nanoTime();
        if (_running && !_paused) _stopTime = curr;
        return _stopTime - _startTime;
    }

    @Override
    public String toString() {
        long nanos = elapsedNanos();
        long hours = TimeUnit.NANOSECONDS.toHours(nanos);
        nanos -= TimeUnit.HOURS.toNanos(hours);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(nanos);
        nanos -= TimeUnit.MINUTES.toNanos(minutes);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(nanos);
        nanos -= TimeUnit.SECONDS.toNanos(seconds);
        long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }

    public static Stopwatch startNew() {
        return new Stopwatch().start();
    }

    private static void assertState(boolean condition) {
        if (!condition) throw new IllegalStateException("Operation invalid at this time");
    }
}
