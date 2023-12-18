package org.dp.utils;

// 抄的苹果Webkit的解贝塞尔曲线的方法
// https://github.com/WebKit/WebKit/blob/main/Source/WebCore/platform/graphics/UnitBezier.h
public class UnitBezier {
    private static double kBezierEpsilon = 1e-7;
    private static int kMaxNewtonIterations = 4;
    private static int CUBIC_BEZIER_SPLINE_SAMPLES = 11;

    private double ax;
    private double bx;
    private double cx;

    private double ay;
    private double by;
    private double cy;

    private double startGradient;
    private double endGradient;

    private double[] splineSamples = new double[CUBIC_BEZIER_SPLINE_SAMPLES];

    public UnitBezier(double p1x, double p1y, double p2x, double p2y)
    {
        // Calculate the polynomial coefficients, implicit first and last control points are (0,0) and (1,1).
        cx = 3.0 * p1x;
        bx = 3.0 * (p2x - p1x) - cx;
        ax = 1.0 - cx -bx;

        cy = 3.0 * p1y;
        by = 3.0 * (p2y - p1y) - cy;
        ay = 1.0 - cy - by;

        // End-point gradients are used to calculate timing function results
        // outside the range [0, 1].
        //
        // There are four possibilities for the gradient at each end:
        // (1) the closest control point is not horizontally coincident with regard to
        //     (0, 0) or (1, 1). In this case the line between the end point and
        //     the control point is tangent to the bezier at the end point.
        // (2) the closest control point is coincident with the end point. In
        //     this case the line between the end point and the far control
        //     point is tangent to the bezier at the end point.
        // (3) both internal control points are coincident with an endpoint. There
        //     are two special case that fall into this category:
        //     CubicBezier(0, 0, 0, 0) and CubicBezier(1, 1, 1, 1). Both are
        //     equivalent to linear.
        // (4) the closest control point is horizontally coincident with the end
        //     point, but vertically distinct. In this case the gradient at the
        //     end point is Infinite. However, this causes issues when
        //     interpolating. As a result, we break down to a simple case of
        //     0 gradient under these conditions.
        if (p1x > 0)
            startGradient = p1y / p1x;
        else if (p1y == 0 && p2x > 0)
            startGradient = p2y / p2x;
        else if (p1y == 0 && p2y == 0)
            startGradient = 1;
        else
            startGradient = 0;
        if (p2x < 1)
            endGradient = (p2y - 1) / (p2x - 1);
        else if (p2y == 1 && p1x < 1)
            endGradient = (p1y - 1) / (p1x - 1);
        else if (p2y == 1 && p1y == 1)
            endGradient = 1;
        else
            endGradient = 0;

        double deltaT = 1.0 / (CUBIC_BEZIER_SPLINE_SAMPLES - 1);
        for (int i = 0; i < CUBIC_BEZIER_SPLINE_SAMPLES; i++)
            splineSamples[i] = sampleCurveX(i * deltaT);
    }

    private double sampleCurveX(double t)
    {
        // `ax t^3 + bx t^2 + cx t' expanded using Horner's rule.
        return ((ax * t + bx) * t + cx) * t;
    }

    private double sampleCurveY(double t)
    {
        return ((ay * t + by) * t + cy) * t;
    }

    private double sampleCurveDerivativeX(double t)
    {
        return (3.0 * ax * t + 2.0 * bx) * t + cx;
    }

    // Given an x value, find a parametric value it came from.
    private double solveCurveX(double x, double epsilon)
    {
        double t0 = 0.0;
        double t1 = 0.0;
        double t2 = x;
        double x2 = 0.0;
        double d2 = 0.0;
        int i = 0;

        // Linear interpolation of spline curve for initial guess.
        double deltaT = 1.0 / (CUBIC_BEZIER_SPLINE_SAMPLES - 1);
        for (i = 1; i < CUBIC_BEZIER_SPLINE_SAMPLES; i++) {
            if (x <= splineSamples[i]) {
                t1 = deltaT * i;
                t0 = t1 - deltaT;
                t2 = t0 + (t1 - t0) * (x - splineSamples[i - 1]) / (splineSamples[i] - splineSamples[i - 1]);
                break;
            }
        }

        // Perform a few iterations of Newton's method -- normally very fast.
        // See https://en.wikipedia.org/wiki/Newton%27s_method.
        double newtonEpsilon = Math.min(kBezierEpsilon, epsilon);
        for (i = 0; i < kMaxNewtonIterations; i++) {
            x2 = sampleCurveX(t2) - x;
            if (Math.abs(x2) < newtonEpsilon)
                return t2;
            d2 = sampleCurveDerivativeX(t2);
            if (Math.abs(d2) < kBezierEpsilon)
                break;
            t2 = t2 - x2 / d2;
        }
        if (Math.abs(x2) < epsilon)
            return t2;

        // Fall back to the bisection method for reliability.
        while (t0 < t1) {
            x2 = sampleCurveX(t2);
            if (Math.abs(x2 - x) < epsilon)
                return t2;
            if (x > x2)
                t0 = t2;
            else
                t1 = t2;
            t2 = (t1 + t0) * .5;
        }

        // Failure.
        return t2;
    }

    public double solve(double x, double epsilon)
    {
        if (x < 0.0)
            return 0.0 + startGradient * x;
        if (x > 1.0)
            return 1.0 + endGradient * (x - 1.0);
        return sampleCurveY(solveCurveX(x, epsilon));
    }

}
