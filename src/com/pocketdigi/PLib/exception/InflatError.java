package com.pocketdigi.PLib.exception;

/**
 * Inflat布局时错误
 */
public class InflatError extends Error{
    public InflatError(String detailMessage) {
        super(detailMessage);
    }
}