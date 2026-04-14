package bms.model.storyboard;

public enum Easing {
    Linear,
    Out,
    In,
    QuadIn, QuadOut, QuadInOut,
    CubicIn, CubicOut, CubicInOut,
    QuartIn, QuartOut, QuartInOut,
    QuintIn, QuintOut, QuintInOut,
    SineIn, SineOut, SineInOut,
    ExpoIn, ExpoOut, ExpoInOut,
    CircIn, CircOut, CircInOut,
    ElasticIn, ElasticOut, ElasticInOut,
    BackIn, BackOut, BackInOut,
    BounceIn, BounceOut, BounceInOut;

    public static float apply(Easing easing, float t) {
        if (t < 0) return 0;
        if (t > 1) return 1;

        switch (easing) {
            case Linear: return t;
            case Out: return t; // Osu default "Out" is often just linear or basic ease out? Actually id 1 is "Out" which usually means "EaseOutQuad"
            case In: return t; // id 2 "In" -> "EaseInQuad"

            case QuadIn: return t * t;
            case QuadOut: return t * (2 - t);
            case QuadInOut: return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;

            case CubicIn: return t * t * t;
            case CubicOut: return (--t) * t * t + 1;
            case CubicInOut: return t < 0.5f ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;

            case QuartIn: return t * t * t * t;
            case QuartOut: return 1 - (--t) * t * t * t;
            case QuartInOut: return t < 0.5f ? 8 * t * t * t * t : 1 - 8 * (--t) * t * t * t;

            case QuintIn: return t * t * t * t * t;
            case QuintOut: return 1 + (--t) * t * t * t * t;
            case QuintInOut: return t < 0.5f ? 16 * t * t * t * t * t : 1 + 16 * (--t) * t * t * t * t;

            case SineIn: return 1 - (float) Math.cos(t * Math.PI / 2);
            case SineOut: return (float) Math.sin(t * Math.PI / 2);
            case SineInOut: return 0.5f * (1 - (float) Math.cos(t * Math.PI));

            case ExpoIn: return (float) Math.pow(2, 10 * (t - 1));
            case ExpoOut: return (float) (-Math.pow(2, -10 * t) + 1);
            case ExpoInOut: return t < 0.5f ? 0.5f * (float) Math.pow(2, 20 * t - 10) : 0.5f * (float) (-Math.pow(2, -20 * t + 10) + 2);

            case CircIn: return (float) (1 - Math.sqrt(1 - t * t));
            case CircOut: return (float) Math.sqrt(1 - (--t) * t);
            case CircInOut: return t < 0.5f ? 0.5f * (float) (1 - Math.sqrt(1 - 4 * t * t)) : 0.5f * (float) (Math.sqrt(1 - (t * 2 - 2) * (t * 2 - 2)) + 1);

            case BackIn:
                float s = 1.70158f;
                return t * t * ((s + 1) * t - s);
            case BackOut:
                s = 1.70158f;
                return (--t) * t * ((s + 1) * t + s) + 1;
            case BackInOut:
                s = 1.70158f * 1.525f;
                if ((t *= 2) < 1) return 0.5f * (t * t * ((s + 1) * t - s));
                return 0.5f * ((t -= 2) * t * ((s + 1) * t + s) + 2);

            case ElasticIn:
            case ElasticOut:
            case ElasticInOut:
            case BounceIn:
            case BounceOut:
            case BounceInOut:
                // TODO: Implement Elastic/Bounce. For now fallback to Linear to prevent crash/freeze.
                return t;

            default: return t;
        }
    }
}
