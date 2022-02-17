package com.coderipper.maib.utils

import androidx.annotation.FloatRange

//           y1 - y0
// y = y0 + --------- (x - x0)
//           x1 - x0

// y = y0 + (((y1 - y0) / (x1 - x0)) * (x - x0))
// x = x0 + (((y - y0) / (y1 - y0)) * (x1 - x0))

// x0 = start value of interpolation
// x1 = end value of interpolation
// y0 = start value known of interpolation
// y1 = end value known of interpolation
// x = value known of interpolation || searching value of interpolation
// y = searching value of interpolation || value known of interpolation
fun linearInterpolation(
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x0: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x1: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y0: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y1: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x: Float
): Float {
    return y0 + (((y1 - y0) / (x1 - x0)) * (x - x0))
}

/*fun linearInterpolationSize(
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x0: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x1: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y0: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y1: Float,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x: Float
): Float {
    if (x < y0) return x0
    if (x > y1) return x1
    return y0 + (((y1 - y0) / (x1 - x0)) * (x - x0))
}*/

fun linearInterpolationCurve(
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x0: Float = 1F,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) x1: Float = 0F,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y0: Float = 0F,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y1: Float = 1F,
    @FloatRange(from = 0.0, fromInclusive = true, to = 1.0, toInclusive = true) y: Float
): Float {
    if (y < y0) return x0
    if (y > y1) return x1

    return x0 + (((y - y0) / (y1 - y0)) * (x1 - x0))
}