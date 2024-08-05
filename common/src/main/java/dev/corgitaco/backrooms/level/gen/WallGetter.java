package dev.corgitaco.backrooms.level.gen;

import org.joml.Matrix2d;
import org.joml.Vector2d;

public class WallGetter {

    private static final float PI_4 = 0.78539815f;
    private static final float PI_2 = 1.5707963f;
    private static final ThreadLocal<Vector2d> uv = ThreadLocal.withInitial(Vector2d::new);
    private static final ThreadLocal<Vector2d> uvFraction = ThreadLocal.withInitial(Vector2d::new);
    private static final ThreadLocal<Vector2d> uvFloor = ThreadLocal.withInitial(Vector2d::new);
    private static final ThreadLocal<Matrix2d> rotationMatrix = ThreadLocal.withInitial(Matrix2d::new);

    public static boolean isWall(double x, double y) {
        float wallFreq = 0.2F;
        float scale = 0.05F;
        float thickness = 1.2F;

        Vector2d uv = WallGetter.uv.get();
        Vector2d uvFraction = WallGetter.uvFraction.get();
        Vector2d uvFloor = WallGetter.uvFloor.get();
        Matrix2d rotationMatrix = WallGetter.rotationMatrix.get();

        uv.set(x, y);
        uv.mul(scale);

        uvFraction.set(fract(uv.x), fract(uv.y));
        uvFloor.set(uv).floor();

        double rotation = PI_2 * getHash(uvFloor, wallFreq) + PI_4;
        double ca = Math.cos(rotation);
        double sa = Math.sin(rotation);
        rotationMatrix.set(ca, sa, -sa, ca);
        uvFraction.mul(rotationMatrix);
        return step(thickness, uvFraction.y - uvFraction.x) == 0 ? true : false;
    }

    private static double getHash(Vector2d uvFloor, double frequency) {
        return Math.floor((fract(Math.sin(uvFloor.x + uvFloor.y * -78.f) * 43758.f) + frequency) * 2.0f);
    }

    private static int step(double edge, double value) {
        return value < edge ? 1 : 0;
    }

    private static double fract(double value) {
        return value - Math.floor(value);
    }
}
